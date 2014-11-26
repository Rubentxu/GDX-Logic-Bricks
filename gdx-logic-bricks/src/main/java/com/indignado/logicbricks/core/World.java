package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.indignado.logicbricks.systems.AnimationSystem;
import com.indignado.logicbricks.systems.RenderingSystem;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.systems.ViewSystem;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.systems.sensors.KeyboardSensorSystem;
import com.indignado.logicbricks.systems.sensors.MouseSensorSystem;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;


/**
 * @author Rubentxu.
 */
public class World implements Disposable {
    private final LogicBricksEngine engine;
    private final AssetManager assetManager;
    private final EntityBuilder entityBuilder;
    private final BodyBuilder bodyBuilder;
    private final com.badlogic.gdx.physics.box2d.World physics;
    private final ObjectMap<Class, EntityPool> entityPools;
    private final IntMap<LevelCreator> levelsCreators;
    private static int levelIndex = 0;


    public World(com.badlogic.gdx.physics.box2d.World physics, AssetManager assetManager,
                 SpriteBatch batch, OrthographicCamera camera) {
        this.physics = physics;
        this.engine = new LogicBricksEngine();

        engine.addSystem(new RenderingSystem(batch, camera, physics));
        engine.addSystem(new ViewSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new KeyboardSensorSystem());
        engine.addSystem(new MouseSensorSystem());
        engine.addSystem(new CollisionSensorSystem());

        InputMultiplexer input = new InputMultiplexer();
        input.addProcessor(engine.getSystem(KeyboardSensorSystem.class));
        input.addProcessor(engine.getSystem(MouseSensorSystem.class));
        Gdx.input.setInputProcessor(input);
        physics.setContactListener(engine.getSystem(CollisionSensorSystem.class));

        this.assetManager = assetManager;
        this.entityBuilder = new EntityBuilder(engine);
        this.bodyBuilder = new BodyBuilder(physics);
        this.entityPools = new ObjectMap<Class, EntityPool>();
        this.levelsCreators = new IntMap<LevelCreator>();
        engine.update(0);

    }


    public void addLevelCreator(LevelCreator level) {
        levelIndex++;
        levelsCreators.put(levelIndex, level);

    }


    public int getLevelsSize() {
        return levelIndex;

    }


    public void createLevel(int levelNumber) {
        engine.removeAllEntities();
        LevelCreator level = levelsCreators.get(levelNumber);
        if (level != null) {
            level.createLevel(this);
        }

    }


    private <T extends LogicEntity> Pool<T> getEntityPool(Class<T> clazz) {
        EntityPool pool = entityPools.get(clazz);
        if (pool == null) {
            pool = new EntityPool(clazz, this);
            entityPools.put(clazz, pool);
        }
        return pool;

    }


    public <T extends LogicEntity> T obtainEntity(Class<T> type) {
        return (T) getEntityPool(type).obtain();

    }


    public void free(LogicEntity entity) {
        if (entity == null) throw new IllegalArgumentException("object cannot be null.");
        EntityPool pool = entityPools.get(entity.getClass());
        if (pool == null) return;
        pool.free(entity);

    }


    public void freeAll(Array entities, boolean samePool) {
        if (entities == null) throw new IllegalArgumentException("objects cannot be null.");
        EntityPool pool = null;
        for (int i = 0, n = entities.size; i < n; i++) {
            Object object = entities.get(i);
            if (object == null) continue;
            if (pool == null) {
                pool = entityPools.get(object.getClass());
                if (pool == null) continue;
            }
            pool.free(object);
            if (!samePool) pool = null;
        }
    }


    public EntityBuilder getEntityBuilder() {
        return entityBuilder;

    }


    public BodyBuilder getBodyBuilder() {
        return bodyBuilder;

    }


    public AssetManager getAssetManager() {
        return assetManager;

    }


    public void update(float deltaTime) {
        engine.update(deltaTime);
        physics.step(deltaTime, 10, 8);

    }


    public void resize(int width, int height) {

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

}
