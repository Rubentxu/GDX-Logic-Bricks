package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.indignado.logicbricks.utils.builders.LBBuilders;


/**
 * @author Rubentxu.
 */
public abstract class EntityFactory {
    protected String tag = this.getClass().getSimpleName();
    protected LBBuilders builders;
    protected AssetManager assetManager;

    public EntityFactory(LBBuilders builders, AssetManager assetManager) {
        this.builders = builders;
        this.assetManager = assetManager;

    }

    public abstract void loadAssets();

    public abstract Entity createEntity();


}
