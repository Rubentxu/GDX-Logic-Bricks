package com.indignado.functional.test.levels.radialgravity.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EngineUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.MotionActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;

/**
 * @author Rubentxu.
 */
public class Box extends EntityFactory {


    public Box(World world) {
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
        identity.tag = "Box";

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body bodyBox = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(MathUtils.random(0.4f,0.8f), MathUtils.random(0.4f,0.8f))
                .friction(1f)
                .density(2f))
                .mass(2)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        AlwaysSensor alwaysSensor = EngineUtils.getBuilder(AlwaysSensorBuilder.class)
                .setPulse(Sensor.Pulse.PM_TRUE)
                .setName("alwaysSensor")
                .getBrick();

        ConditionalController controller = EngineUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        MotionActuator motionActuator = EngineUtils.getBuilder(MotionActuatorBuilder.class)
                .setTorque(5)
                .getBrick();


        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyBox);

        Entity entity = entityBuilder.addController(controller,"Default")
                .connectToSensor(alwaysSensor)
                .connectToActuator(motionActuator)
                .getEntity();

        Gdx.app.log("Box", "instance" + entity);
        return entity;

    }


}
