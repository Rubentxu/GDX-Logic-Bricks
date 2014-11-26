package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
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


    public World(com.badlogic.gdx.physics.box2d.World physics, AssetManager assetManager) {
        this.physics = physics;
        this.engine = new LogicBricksEngine();
        this.assetManager = assetManager;
        this.entityBuilder = new EntityBuilder(engine);
        this.bodyBuilder = new BodyBuilder(physics);
        this.entityPools = new ObjectMap<Class, EntityPool>();

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
