package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.components.actuators.*;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;
import com.indignado.logicbricks.components.sensors.*;
import com.indignado.logicbricks.core.actuators.*;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.*;
import com.indignado.logicbricks.systems.*;
import com.indignado.logicbricks.systems.actuators.*;
import com.indignado.logicbricks.systems.controllers.ConditionalControllerSystem;
import com.indignado.logicbricks.systems.controllers.ControllerSystem;
import com.indignado.logicbricks.systems.controllers.ScriptControllerSystem;
import com.indignado.logicbricks.systems.sensors.*;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BrickBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.joints.JointBuilder;

import java.util.Iterator;


/**
 * @author Rubentxu.
 */
public class Game implements Disposable, ContactListener {
    private static String tag = Game.class.getSimpleName();
    private static int levelIndex = 0;
    private final AssetManager assetManager;
    private final LogicBricksEngine engine;
    private final com.badlogic.gdx.physics.box2d.World physics;
    private final IntMap<LevelFactory> levelFactories;
    private final OrthographicCamera camera;

    // Builders
    private final EntityBuilder entityBuilder;
    private final BodyBuilder bodyBuilder;
    private final JointBuilder jointBuilder;
    private ObjectMap<Class<? extends BrickBuilder>, BrickBuilder> bricksBuilders;

    private ViewSystem viewSystem;
    private SpriteBatch batch;

    private ObjectMap<Class<? extends EntityFactory>, EntityFactory> entityFactories;
    private CategoryBitsManager categoryBitsManager;

    private float fixedTimesStepAccumulator = 0.0f;
    private float fixedTimesStepAccumulatorRatio = 0.0f;
    private int nSteps;
    private int nStepsClamped;


    public Game() {
        this(new AssetManager(), new SpriteBatch());

    }


    public Game(AssetManager assetManager, SpriteBatch batch) {
        this.physics = new World(Settings.GRAVITY, true);
        this.assetManager = assetManager;
        this.camera = new OrthographicCamera();

        this.engine = new LogicBricksEngine(this);
        this.batch = batch;

        entityBuilder = new EntityBuilder(engine);
        bodyBuilder = new BodyBuilder(physics);
        jointBuilder = new JointBuilder(physics);
        bricksBuilders = new ObjectMap<>();

        engine.addSystem(new RenderingSystem());
        viewSystem = new ViewSystem();
        engine.addSystem(viewSystem);


        levelFactories = new IntMap<LevelFactory>();
        entityFactories = new ObjectMap<Class<? extends EntityFactory>, EntityFactory>();
        categoryBitsManager = new CategoryBitsManager();

        engine.update(0);

        Gdx.input.setInputProcessor(engine.getInputs());
        physics.setContactListener(this);
        Gdx.app.setLogLevel(Settings.DEBUG_LEVEL);
        registerDefaultClasses();

    }


    private void registerDefaultClasses() {
        engine.registerBricksClasses(AlwaysSensor.class, AlwaysSensorComponent.class, AlwaysSensorSystem.class);
        engine.registerBricksClasses(CollisionSensor.class, CollisionSensorComponent.class, CollisionSensorSystem.class);
        engine.registerBricksClasses(KeyboardSensor.class, KeyboardSensorComponent.class, KeyboardSensorSystem.class);
        engine.registerBricksClasses(MouseSensor.class, MouseSensorComponent.class, MouseSensorSystem.class);
        engine.registerBricksClasses(PropertySensor.class, PropertySensorComponent.class, PropertySensorSystem.class);
        engine.registerBricksClasses(MessageSensor.class, MessageSensorComponent.class, MessageSensorSystem.class);
        engine.registerBricksClasses(DelaySensor.class, DelaySensorComponent.class, DelaySensorSystem.class);
        engine.registerBricksClasses(RadarSensor.class, RadarSensorComponent.class, RadarSensorSystem.class);
        engine.registerBricksClasses(NearSensor.class, NearSensorComponent.class, NearSensorSystem.class);
        engine.registerBricksClasses(RaySensor.class, RaySensorComponent.class, RaySensorSystem.class);

        engine.registerBricksClasses(Controller.class, ControllerComponent.class, ControllerSystem.class);
        engine.registerBricksClasses(ConditionalController.class, ConditionalControllerComponent.class, ConditionalControllerSystem.class);
        engine.registerBricksClasses(ScriptController.class, ScriptControllerComponent.class, ScriptControllerSystem.class);

        engine.registerBricksClasses(CameraActuator.class, CameraActuatorComponent.class, CameraActuatorSystem.class);
        engine.registerBricksClasses(EditRigidBodyActuator.class, EditRigidBodyActuatorComponent.class, EditRigidBodyActuatorSystem.class);
        engine.registerBricksClasses(EffectActuator.class, EffectActuatorComponent.class, EffectActuatorSystem.class);
        engine.registerBricksClasses(InstanceEntityActuator.class, InstanceEntityActuatorComponent.class, InstanceEntityActuatorSystem.class);
        engine.registerBricksClasses(MessageActuator.class, MessageActuatorComponent.class, MessageActuatorSystem.class);
        engine.registerBricksClasses(MotionActuator.class, MotionActuatorComponent.class, MotionActuatorSystem.class);
        engine.registerBricksClasses(PropertyActuator.class, PropertyActuatorComponent.class, PropertyActuatorSystem.class);
        engine.registerBricksClasses(StateActuator.class, StateActuatorComponent.class, StateActuatorSystem.class);
        engine.registerBricksClasses(TextureActuator.class, TextureActuatorComponent.class, TextureActuatorSystem.class);

        engine.registerEngineClasses(BuoyancyComponent.class, BuoyancySystem.class);
        engine.registerEngineClasses(RadialGravityComponent.class, RadialGravitySystem.class);
        engine.registerEngineClasses(ViewsComponent.class, RenderingSystem.class, ViewSystem.class);
        engine.registerEngineClasses(StateComponent.class, StateSystem.class);

    }

    public void addLevelCreator(LevelFactory level) {
        levelIndex++;
        levelFactories.put(levelIndex, level);

    }


    public int getLevelsSize() {
        return levelIndex;

    }


    public void createLevel(int levelNumber) {
        engine.removeAllEntities();
        LevelFactory level = levelFactories.get(levelNumber);
        if (level != null) {
            level.loadAssets();
            level.createLevel();

        }
        if (Settings.DRAGGABLE_BOX2D_BODIES) engine.addSystem(new DraggableBodySystem());

    }


    public void positioningEntity(Entity entity, float posX, float posY, float angle) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        RigidBodiesComponents rbc = entity.getComponent(RigidBodiesComponents.class);
        for (Body rigidBody : rbc.rigidBodies) {
            Vector2 originPosition = new Vector2(posX, posY);
            originPosition.add(rigidBody.getPosition());
            rigidBody.setTransform(originPosition, (rigidBody.getAngle() + angle) * MathUtils.degreesToRadians);
            Log.debug(tag, "Entity initial position %s", originPosition.toString());

        }

    }


    public AssetManager getAssetManager() {
        return assetManager;

    }


    public LogicBricksEngine getEngine() {
        return engine;

    }


    public com.badlogic.gdx.physics.box2d.World getPhysics() {
        return physics;

    }


    public OrthographicCamera getCamera() {
        return camera;
    }



    public void update() {
        update(Gdx.graphics.getDeltaTime());

    }


    public void update(float dt) {
        fixedTimesStepAccumulator += dt;
        nSteps = MathUtils.round(fixedTimesStepAccumulator / Settings.FIXED_TIME_STEP);
        if(nSteps > 0) fixedTimesStepAccumulator -= nSteps * Settings.FIXED_TIME_STEP;
        fixedTimesStepAccumulatorRatio = fixedTimesStepAccumulator / Settings.FIXED_TIME_STEP;

        nStepsClamped = Math.min(nSteps, Settings.MAX_STEPS);

        for(int i = 0; i < nStepsClamped; ++i) {
            singleStep(Settings.FIXED_TIME_STEP);

        }
        MessageManager.getInstance().update(dt);

    }


    public void singleStep(float dt) {
        physics.step(dt, Settings.BOX2D_VELOCITY_ITERATIONS, Settings.BOX2D_POSITION_ITERATIONS);
        engine.update(dt);

    }


    public <B extends BrickBuilder> B getBuilder(Class<B> clazzBuilder) {
        B builder = (B) bricksBuilders.get(clazzBuilder);
        if (builder == null) {
            synchronized (clazzBuilder) {
                try {
                    Constructor constructor = findConstructor(clazzBuilder);
                    builder = (B) constructor.newInstance((Object[]) null);
                    bricksBuilders.put(clazzBuilder, builder);
                } catch (Exception e) {
                    Log.debug(tag, "Error instance actuatorBuilder %s" + clazzBuilder.getSimpleName());
                }
            }
        }
        return builder;

    }



    private <B extends BrickBuilder> Constructor findConstructor(Class<B> type) {
        try {
            return ClassReflection.getConstructor(type, (Class[]) null);
        } catch (Exception ex1) {
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, (Class[]) null);
                constructor.setAccessible(true);
                return constructor;
            } catch (ReflectionException ex2) {
                return null;
            }
        }

    }


    private void activeLogicBrickSystemProcessing(boolean active) {
        Iterator<EntitySystem> it = engine.getSystems().iterator();
        while (it.hasNext()) {
            EntitySystem system = it.next();
            if (system instanceof LogicBrickSystem) system.setProcessing(active);
        }

    }


    public void resize(int width, int height) {
        this.camera.viewportHeight = Settings.HEIGHT;
        this.camera.viewportWidth = (Settings.HEIGHT / height) * width;

    }


    public void show() {

    }


    public void hide() {

    }


    public void pause() {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(false);
        }
    }


    public void resume() {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(true);
        }
    }


    @Override
    public void dispose() {

    }


    public EntityBuilder getEntityBuilder() {
        return entityBuilder;

    }


    public BodyBuilder getBodyBuilder() {
        return bodyBuilder;

    }


    public JointBuilder getJointBuilder() {
        return jointBuilder;

    }


    public <T extends EntityFactory> void addEntityFactory(T factory) {
        entityFactories.put(factory.getClass(), factory);


    }


    public ObjectMap<Class<? extends EntityFactory>, EntityFactory> getEntityFactories() {
        return entityFactories;

    }


    public CategoryBitsManager getCategoryBitsManager() {
        return categoryBitsManager;

    }


    public SpriteBatch getBatch() {
        return batch;

    }


    @Override
    public void beginContact(Contact contact) {
        for (ContactListener contactListener : engine.getContactSystems()) {
            contactListener.beginContact(contact);

        }

    }


    @Override
    public void endContact(Contact contact) {
        for (ContactListener contactListener : engine.getContactSystems()) {
            contactListener.endContact(contact);

        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        for (ContactListener contactListener : engine.getContactSystems()) {
            contactListener.preSolve(contact, oldManifold);

        }

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (ContactListener contactListener : engine.getContactSystems()) {
            contactListener.postSolve(contact, impulse);

        }

    }

}
