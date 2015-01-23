package com.indignado.functional.test.levels.radialgravity.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RadialGravityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;

/**
 * @author Rubentxu.
 */
public class Planet extends EntityFactory {


    public Planet(Game game) {
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
        identity.tag = "Planet";

        Body bodyPool = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(5f)
                        .density(1)
                        .friction(2f))
                .mass(2f)
                .type(BodyDef.BodyType.StaticBody)
                .build();

        RigidBodiesComponents rigidByPool = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByPool.rigidBodies.add(bodyPool);

        RadialGravityComponent radialGravityComponent = entityBuilder.getComponent(RadialGravityComponent.class);
        radialGravityComponent.radius = 24f;
        // radialGravityComponent.gravity = -18;

        Entity entity = entityBuilder.getEntity();

        Gdx.app.log("Planet", "instance " + entity);
        return entity;

    }


}
