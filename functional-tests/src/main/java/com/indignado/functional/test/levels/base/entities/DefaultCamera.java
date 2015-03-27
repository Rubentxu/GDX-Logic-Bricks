package com.indignado.functional.test.levels.base.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.indignado.logicbricks.components.CameraComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class DefaultCamera extends EntityFactory {


    public DefaultCamera(LBBuilders builders, AssetManager assetManager) {
        super(builders, assetManager);

    }


    @Override
    public void loadAssets() {
    }


    @Override
    public Entity createEntity(float x, float y, float z) {
        EntityBuilder entityBuilder = builders.getEntityBuilder();
        entityBuilder.initialize();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Camera";
        //identity.category = game.getCategoryBitsManager().getCategoryBits("Crate");

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        CameraComponent camera = entityBuilder.getComponent(CameraComponent.class);

        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Crate", "instance" + entity);
        return entity;

    }


}
