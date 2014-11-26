package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.core.actuators.EditRigidBodyActuator;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class EditRigidBodyBrickBuilder extends ActuatorBuilder<EditRigidBodyActuator> {


    public EditRigidBodyBrickBuilder setTargetRigidBody(Body targetRigidBody) {
        brick.targetRigidBody = targetRigidBody;
        return this;

    }


    public EditRigidBodyBrickBuilder setActive(boolean active) {
        brick.active = active;
        return this;

    }


    public EditRigidBodyBrickBuilder setAwake(boolean awake) {
        brick.awake = awake;
        return this;

    }


    public EditRigidBodyBrickBuilder setFriction(float friction) {
        brick.friction = friction;
        return this;

    }


    public EditRigidBodyBrickBuilder setRestitution(float restitution) {
        brick.restitution = restitution;
        return this;

    }


    public EditRigidBodyBrickBuilder setTarget(Entity target) {
        brick.target = target;
        return this;

    }

}
