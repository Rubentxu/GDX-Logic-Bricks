package com.indignado.functional.test.levels.buoyancy.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class Pool extends EntityFactory {


    public Pool(LBBuilders builders, AssetManager assetManager) {
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
        identity.tag = "Pool";

        Body bodyPool = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .boxShape(7, 2.5f)
                        .density(2)
                        .sensor())
                .position(0, 0)
                .mass(1f)
                .type(BodyDef.BodyType.StaticBody)
                .build();

        RigidBodiesComponents rigidByPool = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByPool.rigidBodies.add(bodyPool);

        BuoyancyComponent buoyancyComponent = entityBuilder.getComponent(BuoyancyComponent.class);
        buoyancyComponent.offset = 4f;

        Entity entity = entityBuilder.getEntity();

        Gdx.app.log("Pool", "instance" + entity);
        return entity;

    }


}
