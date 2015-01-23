package com.indignado.functional.test.levels.base.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.joints.JointBuilder;

/**
 * @author Rubentxu.
 */
public class Pulley extends EntityFactory {


    public Pulley(Game game) {
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
        JointBuilder jointBuilder = game.getJointBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Pulley";
        identity.category = game.getCategoryBitsManager().getCategoryBits("Pulley");

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body body1 = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(1, 1)
                .friction(0.5f)
                .restitution(0.5f))
                .type(BodyDef.BodyType.DynamicBody)
                .build();


        Body body2 = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(1, 1)
                .friction(0.5f)
                .restitution(0.5f))
                .position(2, 3)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        Joint joint = jointBuilder.distanceJoint()
                .initialize(body1, body2, body1.getWorldCenter(), body2.getWorldCenter())
                .dampingRatio(1)
                .frequencyHz(3)
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(body1);

        bodiesComponents.rigidBodies.add(body2);

        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Crate", "instance" + entity);
        return entity;

    }


}
