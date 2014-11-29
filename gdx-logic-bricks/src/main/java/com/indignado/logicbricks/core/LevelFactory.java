package com.indignado.logicbricks.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author Rubentxu.
 */
public abstract class LevelFactory {
    protected ObjectMap<Class<? extends EntityFactory>, EntityFactory> entityFactories;


    protected LevelFactory() {
        this.entityFactories = new ObjectMap<Class<? extends EntityFactory>, EntityFactory>();

    }


    public void loadAssets(AssetManager manager) {
        for (EntityFactory factory : entityFactories.values()) {
            factory.loadAssets(manager);
        }
        manager.finishLoading();

    }


    public <T extends EntityFactory> LevelFactory addEntityFactory(T factory) {
        entityFactories.put(factory.getClass(), factory);
        return this;

    }


    public abstract void createLevel(World world);


}
