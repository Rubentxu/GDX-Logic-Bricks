package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author Rubentxu.
 */
public class EditRigidBodyActuator extends Actuator {
    public Body targetRigidBody;
    public boolean active = true;
    public boolean awake = true;
    public float friction = 0.2f;
    public float restitution = 0;
    public Entity target;


    public EditRigidBodyActuator setTargetRigidBody(Body targetRigidBody) {
        this.targetRigidBody = targetRigidBody;
        return this;

    }


    public EditRigidBodyActuator setActive(boolean active) {
        this.active = active;
        return this;

    }


    public EditRigidBodyActuator setAwake(boolean awake) {
        this.awake = awake;
        return this;

    }


    public EditRigidBodyActuator setFriction(float friction) {
        this.friction = friction;
        return this;

    }


    public EditRigidBodyActuator setRestitution(float restitution) {
        this.restitution = restitution;
        return this;

    }


    public EditRigidBodyActuator setTarget(Entity target) {
        this.target = target;
        return this;

    }

}