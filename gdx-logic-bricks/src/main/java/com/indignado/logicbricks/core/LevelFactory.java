package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.RigidBodiesComponents;

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
