package com.indignado.functional.test.levels.base.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.LightComponent;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class DefaultLight extends EntityFactory {


    public DefaultLight(LBBuilders builders, AssetManager assetManager) {
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
        identity.tag = "Light";

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        LightComponent light = entityBuilder.getComponent(LightComponent.class);
        light.light = builders.getLightBuilder().lightType("PointLight").raysNum(500).distance(35)
                .color(new Color(MathUtils.random(1f), MathUtils.random(1f), MathUtils.random(1f),MathUtils.random(0.6f,1f)))
                .build(x,y);

        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Light", "instance" + entity);
        return entity;

    }


}
