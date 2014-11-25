package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.core.actuators.EditRigidBodyActuator;

/**
 * @author Rubentxu.
 */
public class EditRigidBodyActuatorBuilder extends ActuatorBuilder<EditRigidBodyActuator> {


    public EditRigidBodyActuatorBuilder setTargetRigidBody(Body targetRigidBody) {
        actuator.targetRigidBody = targetRigidBody;
        return this;

    }


    public EditRigidBodyActuatorBuilder setActive(boolean active) {
        actuator.active = active;
        return this;

    }


    public EditRigidBodyActuatorBuilder setAwake(boolean awake) {
        actuator.awake = awake;
        return this;

    }


    public EditRigidBodyActuatorBuilder setFriction(float friction) {
        actuator.friction = friction;
        return this;

    }


    public EditRigidBodyActuatorBuilder setRestitution(float restitution) {
        actuator.restitution = restitution;
        return this;

    }


    public EditRigidBodyActuatorBuilder setTarget(Entity target) {
        actuator.target = target;
        return this;

    }

}
