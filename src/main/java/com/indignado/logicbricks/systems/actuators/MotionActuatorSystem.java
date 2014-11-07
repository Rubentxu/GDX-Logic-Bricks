package com.indignado.logicbricks.systems.actuators;

import com.indignado.logicbricks.bricks.actuators.MotionActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.MotionActuatorComponent;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class MotionActuatorSystem extends ActuatorSystem<MotionActuator, MotionActuatorComponent> {


    public MotionActuatorSystem() {
        super(MotionActuatorComponent.class);

    }


    @Override
    public void processActuator(MotionActuator actuator) {
        if (evaluateController(actuator)) {
            if (actuator.targetRigidBody == null) {
                actuator.targetRigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
            }
            if (actuator.velocity != null) actuator.targetRigidBody.setLinearVelocity(actuator.velocity);
            if (actuator.force != null)
                actuator.targetRigidBody.applyForce(actuator.force, actuator.targetRigidBody.getWorldCenter(), true);
            if (actuator.impulse != null)
                actuator.targetRigidBody.applyLinearImpulse(actuator.impulse, actuator.targetRigidBody.getWorldCenter(), true);

            if (!actuator.fixedRotation) {
                if (actuator.angularVelocity != 0)
                    actuator.targetRigidBody.setAngularVelocity(actuator.angularVelocity);
                if (actuator.torque != 0) actuator.targetRigidBody.applyTorque(actuator.torque, true);
                if (actuator.angularImpulse != 0)
                    actuator.targetRigidBody.applyAngularImpulse(actuator.angularImpulse, true);
            } else {
                if (!actuator.targetRigidBody.isFixedRotation()) actuator.targetRigidBody.setFixedRotation(true);
            }
        }

    }


}
