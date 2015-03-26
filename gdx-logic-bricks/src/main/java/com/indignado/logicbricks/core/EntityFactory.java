package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
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


    public Entity createEntity() {
        return createEntity(new Vector3());

    }


    public abstract Entity createEntity(Vector3 vector3);


}
