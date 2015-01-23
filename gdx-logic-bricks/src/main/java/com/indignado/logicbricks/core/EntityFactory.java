package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;


/**
 * @author Rubentxu.
 */
public abstract class EntityFactory {
    protected String tag = this.getClass().getSimpleName();
    protected Game game;

    public EntityFactory(Game game) {
        this.game = game;

    }

    public abstract void loadAssets();

    public abstract Entity createEntity();


}
