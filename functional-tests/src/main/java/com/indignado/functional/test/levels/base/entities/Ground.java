package com.indignado.functional.test.levels.base.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
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
public class Ground extends EntityFactory {


    public Ground(World world) {
        super(world);

    }


    @Override
    public void loadAssets() {}


    @Override
    public Entity createEntity() {
        Gdx.app.log("Ground","init instance");
        EntityBuilder entityBuilder = world.getEntityBuilder();
        BodyBuilder bodyBuilder = world.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Ground";
        identity.category = world.getCategoryBitsManager().getCategoryBits("Ground");


        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");
        Gdx.app.log("Ground","init instance2");
        Body bodyWall = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(50, 0.6f)
                .friction(0.5f))
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyWall);
        Gdx.app.log("Ground","init instance3");

        Entity entity = entityBuilder.build();
        Gdx.app.log("Ground","instance" + entity);
        return entity;

    }


}
