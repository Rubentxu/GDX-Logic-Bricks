package com.indignado.logicbricks.core;

/**
 * @author Rubentxu.
 */
public abstract class LevelFactory {
    protected String tag = this.getClass().getSimpleName();
    protected World world;


    protected LevelFactory(World world) {
        this.world = world;

    }


    public void loadAssets() {
        for (EntityFactory factory : world.getEntityFactories().values()) {
            factory.loadAssets();
        }
        world.getAssetManager().finishLoading();

    }


    public abstract void createLevel();


}
