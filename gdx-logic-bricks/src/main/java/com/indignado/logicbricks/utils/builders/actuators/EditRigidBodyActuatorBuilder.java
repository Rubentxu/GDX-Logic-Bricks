package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.actuators.EditRigidBodyActuator;
import com.indignado.logicbricks.core.data.RigidBody2D;

/**
 * @author Rubentxu.
 */
public class EditRigidBodyActuatorBuilder extends ActuatorBuilder<EditRigidBodyActuator> {

    public EditRigidBodyActuatorBuilder() {
        brick = new EditRigidBodyActuator();
    }

    public EditRigidBodyActuatorBuilder setTargetRigidBody(RigidBody2D targetRigidBody) {
        brick.targetRigidBody = targetRigidBody;
        return this;

    }


    public EditRigidBodyActuatorBuilder setActive(boolean active) {
        brick.active = active;
        return this;

    }


    public EditRigidBodyActuatorBuilder setAwake(boolean awake) {
        brick.awake = awake;
        return this;

    }


    public EditRigidBodyActuatorBuilder setFriction(float friction) {
        brick.friction = friction;
        return this;

    }


    public EditRigidBodyActuatorBuilder setRestitution(float restitution) {
        brick.restitution = restitution;
        return this;

    }


    public EditRigidBodyActuatorBuilder setTarget(Entity target) {
        brick.target = target;
        return this;

    }

    @Override
    public EditRigidBodyActuator getBrick() {
        EditRigidBodyActuator brickTemp = brick;
        brick = new EditRigidBodyActuator();
        return brickTemp;

    }

}
