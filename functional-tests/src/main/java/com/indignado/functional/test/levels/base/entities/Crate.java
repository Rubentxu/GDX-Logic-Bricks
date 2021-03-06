package com.indignado.functional.test.levels.base.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class Crate extends EntityFactory {


    public Crate(LBBuilders builders, AssetManager assetManager) {
        super(builders, assetManager);

    }


    @Override
    public void loadAssets() {
    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = builders.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = builders.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Crate";
        //identity.category = game.getCategoryBitsManager().getCategoryBits("Crate");

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body bodyCrate = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(1, 1)
                .friction(0.5f)
                .restitution(0.5f))
                .type(BodyDef.BodyType.StaticBody)
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyCrate);

        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Crate", "instance" + entity);
        return entity;

    }


}
