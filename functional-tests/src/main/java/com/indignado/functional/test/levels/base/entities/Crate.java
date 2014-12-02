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
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu.
 */
public class Crate implements EntityFactory {


    @Override
    public void loadAssets(AssetManager manager) {}


    @Override
    public Entity createEntity(World world) {
        EntityBuilder entityBuilder = world.getEntityBuilder();
        BodyBuilder bodyBuilder = world.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Crate";

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body bodyCrate = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(1, 20)
                .friction(0.5f)
                .restitution(0.5f))
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyCrate);

        Entity entity = entityBuilder.build();
        Gdx.app.log("Crate","instance" + entity);
        return entity;

    }


}
