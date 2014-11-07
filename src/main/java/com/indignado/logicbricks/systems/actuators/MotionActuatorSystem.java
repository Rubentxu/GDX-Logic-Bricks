package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.indignado.logicbricks.bricks.actuators.MotionActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.CameraActuatorComponent;
import com.indignado.logicbricks.components.actuators.MotionActuatorComponent;

import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class MotionActuatorSystem extends ActuatorSystem {
    private ComponentMapper<MotionActuatorComponent> motionActuatorMapper;
    private ComponentMapper<StateComponent> stateMapper;


    public MotionActuatorSystem() {
        super(Family.getFor(CameraActuatorComponent.class, StateComponent.class));
        motionActuatorMapper = ComponentMapper.getFor(MotionActuatorComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).get();
        Set<MotionActuator> actuators = motionActuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (MotionActuator actuator : actuators) {
                if (evaluateController(actuator)) execute(actuator);
            }
        }

    }


    public void execute(MotionActuator actuator) {
        if (actuator.targetRigidBody == null) {
            actuator.targetRigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
        }
        if (actuator.velocity != null) actuator.targetRigidBody.setLinearVelocity(actuator.velocity);
        if (actuator.force != null)
            actuator.targetRigidBody.applyForce(actuator.force, actuator.targetRigidBody.getWorldCenter(), true);
        if (actuator.impulse != null)
            actuator.targetRigidBody.applyLinearImpulse(actuator.impulse, actuator.targetRigidBody.getWorldCenter(), true);

        if (!actuator.fixedRotation) {
            if (actuator.angularVelocity != 0) actuator.targetRigidBody.setAngularVelocity(actuator.angularVelocity);
            if (actuator.torque != 0) actuator.targetRigidBody.applyTorque(actuator.torque, true);
            if (actuator.angularImpulse != 0)
                actuator.targetRigidBody.applyAngularImpulse(actuator.angularImpulse, true);
        } else {
            if (!actuator.targetRigidBody.isFixedRotation()) actuator.targetRigidBody.setFixedRotation(true);
        }

    }

}
