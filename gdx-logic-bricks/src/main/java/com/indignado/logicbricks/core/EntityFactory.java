package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;

/**
 * @author Rubentxu.
 */
public interface EntityFactory {

    public void loadAssets(AssetManager manager);

    public Entity createEntity(World world);


}
