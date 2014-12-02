package com.indignado.functional.test.levels.base.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.MotionActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;

/**
 * @author Rubentxu.
 */
public class Wall implements EntityFactory {


    @Override
    public void loadAssets(AssetManager manager) {}


    @Override
    public Entity createEntity(World world) {
        EntityBuilder entityBuilder = world.getEntityBuilder();
        BodyBuilder bodyBuilder = world.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Wall";

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body bodyWall = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(1, 20)
                .friction(0.5f)
                .restitution(0.5f))
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyWall);

        Entity entity = entityBuilder.build();
        Gdx.app.log("Wall","instance" + entity);
        return entity;

    }


}
