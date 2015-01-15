package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.systems.*;
import com.indignado.logicbricks.systems.actuators.InstanceEntityActuatorSystem;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.systems.sensors.MouseSensorSystem;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;

import java.util.Iterator;


/**
 * @author Rubentxu.
 */
public class World implements Disposable, ContactListener {
    private static int levelIndex = 0;
    private final AssetManager assetManager;
    private final com.badlogic.gdx.physics.box2d.World physics;
    private final IntMap<LevelFactory> levelFactories;
    private final OrthographicCamera camera;
    private final EntityBuilder entityBuilder;
    private final BodyBuilder bodyBuilder;
    // Systems
    private final ViewPositionSystem viewPositionSystem;
    private final SpriteBatch batch;
    private String tag = this.getClass().getSimpleName();
    private LogicBricksEngine engine;
    private ObjectMap<Class<? extends EntityFactory>, EntityFactory> entityFactories;
    private CategoryBitsManager categoryBitsManager;
    private double currentTime;
    private double accumulatorPhysics;


    public World(com.badlogic.gdx.physics.box2d.World physics, AssetManager assetManager,
                 SpriteBatch batch, OrthographicCamera camera) {
        this.physics = physics;
        this.assetManager = assetManager;
        this.camera = camera;
        this.engine = new LogicBricksEngine();
        this.batch = batch;
        engine.addSystem(new RenderingSystem(batch, camera, physics));
        viewPositionSystem = new ViewPositionSystem();
        engine.addSystem(viewPositionSystem);
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem(this));
        engine.addSystem(new MouseSensorSystem(this));
        engine.addSystem(new InstanceEntityActuatorSystem(this));
        engine.addSystem(new CollisionSensorSystem());

        entityBuilder = new EntityBuilder(engine);
        bodyBuilder = new BodyBuilder(physics);
        this.levelFactories = new IntMap<LevelFactory>();
        this.entityFactories = new ObjectMap<Class<? extends EntityFactory>, EntityFactory>();
        this.categoryBitsManager = new CategoryBitsManager();
        engine.update(0);
        currentTime = TimeUtils.millis() / 1000.0;
        accumulatorPhysics = 0.0;

        Gdx.input.setInputProcessor(engine.getInputs());
        physics.setContactListener(this);
        Gdx.app.setLogLevel(Settings.debugLevel);

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

    }


    public void positioningEntity(Entity entity, float posX, float posY, float angle) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
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
        double newTime = TimeUtils.millis() / 1000.0;
        double frameTime = Math.min(newTime - currentTime, 0.25);
        float deltaTime = (float) frameTime;

        currentTime = newTime;
        accumulatorPhysics += frameTime;

        while (accumulatorPhysics >= Settings.physicsDeltaTime) {
            physics.step(Settings.physicsDeltaTime, Settings.velocityIterations, Settings.positionIterations);
            accumulatorPhysics -= Settings.physicsDeltaTime;
            viewPositionSystem.setAlpha((float) (accumulatorPhysics / Settings.physicsDeltaTime));

        }
        engine.update(deltaTime);

    }


    private void activeLogicBrickSystemProcessing(boolean active) {
        Iterator<EntitySystem> it = engine.getSystems().iterator();
        while (it.hasNext()) {
            EntitySystem system = it.next();
            if (system instanceof LogicBrickSystem) system.setProcessing(active);
        }

    }


    public void resize(int width, int height) {
        this.camera.viewportHeight = Settings.Height;
        this.camera.viewportWidth = (Settings.Height / height) * width;

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
