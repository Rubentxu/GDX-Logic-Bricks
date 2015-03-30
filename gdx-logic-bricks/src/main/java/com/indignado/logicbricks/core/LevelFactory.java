package com.indignado.logicbricks.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author Rubentxu.
 */
public abstract class LevelFactory {
    protected String tag = this.getClass().getSimpleName();
    protected ObjectMap<Class<? extends EntityFactory>, EntityFactory> entitiesFactories;
    protected AssetManager assetManager;
    protected LogicBricksEngine engine;


    protected LevelFactory(LogicBricksEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
        entitiesFactories = new ObjectMap<>();

    }


    public void loadAssets() {
        for (EntityFactory factory : entitiesFactories.values()) {
            factory.loadAssets();
        }
        assetManager.finishLoading();

    }


    public <T extends EntityFactory> void addEntityFactory(T factory) {
        entitiesFactories.put(factory.getClass(), factory);

    }



    public abstract void createLevel();


}
