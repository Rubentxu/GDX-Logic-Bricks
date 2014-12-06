package com.indignado.functional.test.levels.base.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu.
 */
public class Wall extends EntityFactory {


    public Wall(World world) {
        super(world);

    }


    @Override
    public void loadAssets() {}


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = world.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = world.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Wall";
        identity.category = world.getCategoryBitsManager().getCategoryBits("Wall");

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body bodyWall = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(1, 20)
                .friction(0.5f)
                .restitution(0.5f))
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyWall);

        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Wall","instance" + entity);
        return entity;

    }


}
