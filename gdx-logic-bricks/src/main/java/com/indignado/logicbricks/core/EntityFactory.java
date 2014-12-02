package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;


/**
 * @author Rubentxu.
 */
public interface EntityFactory {

    public abstract void loadAssets(AssetManager manager);

    public abstract Entity createEntity(World world);


}
