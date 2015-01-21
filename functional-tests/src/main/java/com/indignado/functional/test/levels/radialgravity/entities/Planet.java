package com.indignado.functional.test.levels.radialgravity.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RadialGravityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;

/**
 * @author Rubentxu.
 */
public class Planet extends EntityFactory {


    public Planet(World world) {
        super(world);
    }


    @Override
    public void loadAssets() {
    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = world.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = world.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Planet";

        Body bodyPool = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(4f)
                        .density(1))
                .mass(1f)
                .type(BodyDef.BodyType.StaticBody)
                .build();

        RigidBodiesComponents rigidByPool = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByPool.rigidBodies.add(bodyPool);

        RadialGravityComponent radialGravityComponent = entityBuilder.getComponent(RadialGravityComponent.class);
        radialGravityComponent.radius = 11f;

        Entity entity = entityBuilder.getEntity();

        Gdx.app.log("Planet", "instance " + entity);
        return entity;

    }


}
