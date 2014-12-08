package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.systems.AnimationSystem;
import com.indignado.logicbricks.systems.RenderingSystem;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.systems.ViewPositionSystem;
import com.indignado.logicbricks.systems.actuators.InstanceEntityActuatorSystem;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.systems.sensors.KeyboardSensorSystem;
import com.indignado.logicbricks.systems.sensors.MessageSensorSystem;
import com.indignado.logicbricks.systems.sensors.MouseSensorSystem;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;


/**
 * @author Rubentxu.
 */
public class World implements Disposable {
    private String tag = this.getClass().getSimpleName();
    private static int levelIndex = 0;
    private final AssetManager assetManager;
    private final com.badlogic.gdx.physics.box2d.World physics;
    private final IntMap<LevelFactory> levelFactories;
    private final OrthographicCamera camera;
    private final EntityBuilder entityBuilder;
    private final BodyBuilder bodyBuilder;
    private LogicBricksEngine engine;
    private ObjectMap<Class<? extends EntityFactory>, EntityFactory> entityFactories;
    private CategoryBitsManager categoryBitsManager;


    public World(com.badlogic.gdx.physics.box2d.World physics, AssetManager assetManager,
                 SpriteBatch batch, OrthographicCamera camera) {
        this.physics = physics;
        this.assetManager = assetManager;
        this.camera = camera;
        this.engine = new LogicBricksEngine();
        engine.addSystem(new RenderingSystem(batch, camera, physics));
        engine.addSystem(new ViewPositionSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem(this));
        engine.addSystem(new KeyboardSensorSystem());
        engine.addEntityListener(engine.getSystem(KeyboardSensorSystem.class));
        engine.addSystem(new MouseSensorSystem(this));
        engine.addEntityListener(engine.getSystem(MouseSensorSystem.class));
        engine.addSystem(new CollisionSensorSystem());
        engine.addEntityListener(engine.getSystem(CollisionSensorSystem.class));
        engine.addSystem(new MessageSensorSystem());
        engine.addEntityListener(engine.getSystem(MessageSensorSystem.class));
        engine.addSystem(new InstanceEntityActuatorSystem(this));

        InputMultiplexer input = new InputMultiplexer();
        input.addProcessor(engine.getSystem(KeyboardSensorSystem.class));
        input.addProcessor(engine.getSystem(MouseSensorSystem.class));
        Gdx.input.setInputProcessor(input);
        physics.setContactListener(engine.getSystem(CollisionSensorSystem.class));

        entityBuilder = new EntityBuilder(engine);
        bodyBuilder = new BodyBuilder(physics);
        this.levelFactories = new IntMap<LevelFactory>();
        this.entityFactories = new ObjectMap<Class<? extends EntityFactory>, EntityFactory>();
        this.categoryBitsManager = new CategoryBitsManager();
        engine.update(0);
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
        if(Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(),entity);
        RigidBodiesComponents rbc = entity.getComponent(RigidBodiesComponents.class);
        for (Body rigidBody : rbc.rigidBodies) {
            Vector2 originPosition = new Vector2(posX, posY);
            originPosition.add(rigidBody.getPosition());
            rigidBody.setTransform(originPosition, (rigidBody.getAngle() + angle) * MathUtils.degreesToRadians);
            Log.debug(tag,"Entity initial position %s",originPosition.toString());

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


    public void update(float deltaTime) {
        engine.update(deltaTime);
        physics.step(deltaTime, 10, 8);

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

}
