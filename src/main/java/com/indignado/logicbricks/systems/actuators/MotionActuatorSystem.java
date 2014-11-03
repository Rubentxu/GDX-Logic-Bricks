package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.actuators.MotionActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.systems.LogicBricksSystem;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class MotionActuatorSystem extends LogicBricksSystem {

    public MotionActuatorSystem() {
        super();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        for (MotionActuator actuator : getActuators(MotionActuator.class, entity)) {
            execute(actuator);

        }

    }


    public void execute(MotionActuator actuator) {
        if(actuator.targetRigidBody == null) {
            actuator.targetRigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
        }
        if(actuator.velocity != null ) actuator.targetRigidBody.setLinearVelocity(actuator.velocity);
        if(actuator.force != null ) actuator.targetRigidBody.applyForce(actuator.force,actuator.targetRigidBody.getWorldCenter(),true);
        if(actuator.impulse != null ) actuator.targetRigidBody.applyLinearImpulse(actuator.impulse,actuator.targetRigidBody.getWorldCenter(),true);

        if(!actuator.fixedRotation ){
            if(actuator.angularVelocity != 0 ) actuator.targetRigidBody.setAngularVelocity(actuator.angularVelocity);
            if(actuator.torque != 0 ) actuator.targetRigidBody.applyTorque(actuator.torque,true);
            if(actuator.angularImpulse != 0 ) actuator.targetRigidBody.applyAngularImpulse(actuator.angularImpulse,true);
        } else {
            if(!actuator.targetRigidBody.isFixedRotation()) actuator.targetRigidBody.setFixedRotation(true);
        }

    }

}
