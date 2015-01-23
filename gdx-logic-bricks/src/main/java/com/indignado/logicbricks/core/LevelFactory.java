package com.indignado.logicbricks.core;

/**
 * @author Rubentxu.
 */
public abstract class LevelFactory {
    protected String tag = this.getClass().getSimpleName();
    protected Game game;


    protected LevelFactory(Game game) {
        this.game = game;

    }


    public void loadAssets() {
        for (EntityFactory factory : game.getEntityFactories().values()) {
            factory.loadAssets();
        }
        game.getAssetManager().finishLoading();

    }


    public abstract void createLevel();


}
