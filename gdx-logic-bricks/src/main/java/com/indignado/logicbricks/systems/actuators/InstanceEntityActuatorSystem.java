package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.InstanceEntityActuatorComponent;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.actuators.StateActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.RigidBody;
import com.indignado.logicbricks.core.data.RigidBody2D;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.DelaySensor;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.LBBuilders;
import com.indignado.logicbricks.utils.builders.actuators.MotionActuatorBuilder;
import com.indignado.logicbricks.utils.builders.actuators.StateActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.DelaySensorBuilder;

/**
 * @author Rubentxu
 */
public class InstanceEntityActuatorSystem extends ActuatorSystem<InstanceEntityActuator, InstanceEntityActuatorComponent> {
    private LBBuilders builders;
    private AssetManager assetManager;

    public InstanceEntityActuatorSystem() {
        super(InstanceEntityActuatorComponent.class);

    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        builders = context.get(LBBuilders.class);
        assetManager = context.get(AssetManager.class);

    }


    private Constructor findConstructor(Class type) {
        try {
            return ClassReflection.getConstructor(type, (Class[]) null);
        } catch (Exception ex1) {
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, LBBuilders.class, AssetManager.class);
                constructor.setAccessible(true);
                return constructor;
            } catch (ReflectionException ex2) {
                Log.debug(tag, "Error instance entitySystem %s", ex2.getMessage());
                return null;
            }
        }
    }


    private <T extends EntityFactory> Entity createEntity(Class<T> classFactory) {
        Constructor constructor = findConstructor(classFactory);
        T factory;
        try {
            factory = (T) constructor.newInstance(builders, assetManager);
        } catch (Exception ex) {
            Log.debug(tag, "Error instance entityFactory %s", classFactory.getSimpleName());
            return null;
        }
        return factory.createEntity();

    }


    @Override
    public void processActuator(InstanceEntityActuator actuator, float deltaTime) {
        if (actuator.type == InstanceEntityActuator.Type.AddEntity) {
            Entity entity = createEntity(actuator.entityFactory);
            RigidBody rigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
            if(rigidBody instanceof RigidBody2D) {
                Vector2 position = ((RigidBody2D) rigidBody).body.getPosition().cpy();
                if (actuator.localPosition != null) position.add(actuator.localPosition);

                positioningEntity(entity, position.x, position.y, actuator.angle);
                Log.debug(tag, "Create with position %s", position);

            }

            if (actuator.initialVelocity != null) {
                addMotionComponents(entity, actuator);
            }
            if (actuator.duration != 0) addDurationComponents(entity, actuator);
            engine.addEntity(entity);

        }

    }


    public void positioningEntity(Entity entity, float posX, float posY, float angle) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        RigidBodiesComponents rbc = entity.getComponent(RigidBodiesComponents.class);
        for (RigidBody rigidBody : rbc.rigidBodies) {
            if(rigidBody instanceof RigidBody2D) {
                Vector2 originPosition = new Vector2(posX, posY);
                originPosition.add(((RigidBody2D) rigidBody).body.getPosition());
                ((RigidBody2D) rigidBody).body.setTransform(originPosition, (((RigidBody2D) rigidBody).body.getAngle() + angle) * MathUtils.degreesToRadians);
                Log.debug(tag, "Entity initial position %s", originPosition.toString());
            }

        }

    }


    private void addDurationComponents(Entity entity, InstanceEntityActuator actuator) {
        DelaySensor delaySensor = builders.getBrickBuilder(DelaySensorBuilder.class)
                .setDelay(actuator.duration)
                .setName("SensorDestroyEntity")
                .getBrick();

        StateActuator stateActuator = builders.getBrickBuilder(StateActuatorBuilder.class)
                .setChangeState(StateComponent.eraseID)
                .setName("ChangeStateRemove")
                .getBrick();

        ConditionalController controller = builders.getBrickBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("DurationController")
                .getBrick();

        builders.getEntityBuilder()
                .initialize(entity)
                .addController(controller, entity.getComponent(StateComponent.class).getStates())
                .connectToSensor(delaySensor)
                .connectToActuator(stateActuator)
                .getEntity();


    }


    private void addMotionComponents(Entity entity, InstanceEntityActuator actuator) {
        AlwaysSensor alwaysSensor = builders.getBrickBuilder(AlwaysSensorBuilder.class)
                .setName("SensorMotionActuator")
                .getBrick();

        ConditionalController controller = builders.getBrickBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("ControllerMotionActuator")
                .getBrick();

        MotionActuator motionActuator = builders.getBrickBuilder(MotionActuatorBuilder.class)
                .setVelocity(actuator.initialVelocity)
                .setName("MotionActuatorInstance")
                .getBrick();

        Log.debug(tag, "Initial Velocity %s Angle %f", actuator.initialVelocity.toString(), actuator.angle);

        builders.getEntityBuilder()
                .initialize(entity)
                .addController(controller, entity.getComponent(StateComponent.class).getStates())
                .connectToSensor(alwaysSensor)
                .connectToActuator(motionActuator)
                .getEntity();


    }

}
