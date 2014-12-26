package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.InstanceEntityActuatorComponent;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.actuators.StateActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.TimerSensor;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.actuators.MotionActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.TimerSensorBuilder;

/**
 * @author Rubentxu
 */
public class InstanceEntityActuatorSystem extends ActuatorSystem<InstanceEntityActuator, InstanceEntityActuatorComponent> {
    private World world;


    public InstanceEntityActuatorSystem(World world) {
        super(InstanceEntityActuatorComponent.class);
        this.world = world;

    }


    @Override
    public void processActuator(InstanceEntityActuator actuator, float deltaTime) {
        if (actuator.type == InstanceEntityActuator.Type.AddEntity) {
            Entity entity = actuator.entityFactory.createEntity();
            Body body = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
            Vector2 position = body.getPosition().cpy();
            if (actuator.localPosition != null) position.add(actuator.localPosition);

            world.positioningEntity(entity, position.x, position.y, actuator.angle);
            Log.debug(tag, "Create with position %s", position);

            if (actuator.initialVelocity != null) {
                addMotionComponents(world, entity, actuator);
            }

            world.getEngine().addEntity(entity);
            if (actuator.duration != 0) addDurationComponents(world, entity, actuator);
        }


    }


    private void addDurationComponents(World world, Entity entity, InstanceEntityActuator actuator) {
        TimerSensor timeSensor = BricksUtils.getBuilder(TimerSensorBuilder.class)
                .setDuration(actuator.duration)
                .getBrick();

        StateActuator stateActuator = new StateActuator();
        stateActuator.state = StateComponent.eraseID;

        ConditionalController controller = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("DurationController")
                .getBrick();

        world.getEntityBuilder()
                .initialize(entity)
                .addController(controller, entity.getComponent(StateComponent.class).getStates())
                .connectToSensor(timeSensor)
                .connectToActuator(stateActuator)
                .getEntity();


    }


    private void addMotionComponents(World world, Entity entity, InstanceEntityActuator actuator) {
        TimerSensor timeSensor = BricksUtils.getBuilder(TimerSensorBuilder.class)
                .getBrick();

        ConditionalController controller = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        MotionActuator motionActuator = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setVelocity(actuator.initialVelocity)
                .getBrick();

        Log.debug(tag, "Initial Velocity %s Angle %f", actuator.initialVelocity.toString(), actuator.angle);

        world.getEntityBuilder()
                .initialize(entity)
                .addController(controller, entity.getComponent(StateComponent.class).getStates())
                .connectToSensor(timeSensor)
                .connectToActuator(motionActuator)
                .getEntity();


    }

}
