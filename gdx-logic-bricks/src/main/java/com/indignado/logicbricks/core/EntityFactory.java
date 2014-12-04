package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ObjectMap;


/**
 * @author Rubentxu.
 */
public abstract class EntityFactory {
    protected World world;

    public EntityFactory(World world) {
        this.world = world;

    }

    public abstract void loadAssets();

    public abstract Entity createEntity();


}
