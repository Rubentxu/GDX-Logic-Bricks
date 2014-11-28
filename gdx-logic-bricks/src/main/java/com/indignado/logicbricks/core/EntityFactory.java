package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public interface EntityFactory {

    public void loadAssets(AssetManager manager);


    public Entity createEntity(World world);


    public void init(float posX, float posY, float angle);


}
