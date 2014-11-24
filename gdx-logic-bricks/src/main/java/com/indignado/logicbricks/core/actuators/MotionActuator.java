package com.indignado.logicbricks.core.actuators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author Rubentxu.
 */
public class MotionActuator extends Actuator {
    public Body targetRigidBody;
    public Vector2 velocity;
    public Vector2 force;
    public Vector2 impulse;

    public float angularVelocity = 0;
    public float torque = 0;
    public float angularImpulse = 0;

    public boolean fixedRotation = false;
    public float limitVelocityX = 0;
    public float limitVelocityY = 0;


    public MotionActuator setTargetRigidBody(Body targetRigidBody) {
        this.targetRigidBody = targetRigidBody;
        return this;

    }

    public MotionActuator setVelocity(Vector2 velocity) {
        this.velocity = velocity;
        return this;

    }

    public MotionActuator setForce(Vector2 force) {
        this.force = force;
        return this;

    }

    public MotionActuator setImpulse(Vector2 impulse) {
        this.impulse = impulse;
        return this;

    }


    public MotionActuator setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
        return this;

    }


    public MotionActuator setTorque(float torque) {
        this.torque = torque;
        return this;

    }


    public MotionActuator setAngularImpulse(float angularImpulse) {
        this.angularImpulse = angularImpulse;
        return this;

    }


    public MotionActuator setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
        return this;

    }


    public MotionActuator setLimitVelocityX(float limitVelocityX) {
        this.limitVelocityX = limitVelocityX;
        return this;

    }


    public MotionActuator setLimitVelocityY(float limitVelocityY) {
        this.limitVelocityY = limitVelocityY;
        return this;

    }

}
