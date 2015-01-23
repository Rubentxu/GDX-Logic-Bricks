package com.indignado.functional.test.levels.buoyancy.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu.
 */
public class Box extends EntityFactory {


    public Box(Game game) {
        super(game);

    }


    @Override
    public void loadAssets() {
    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = game.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = game.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Box";

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body bodyBox = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(MathUtils.random(0.5f, 2), MathUtils.random(0.5f, 2.5f))
                .friction(0.5f)
                .density(1f))
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyBox);

        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Box", "instance" + entity);
        return entity;

    }


}
