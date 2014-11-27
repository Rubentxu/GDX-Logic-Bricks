package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.data.RigidBody;
import com.indignado.logicbricks.core.actuators.MotionActuator;

/**
 * @author Rubentxu.
 */
public class MotionActuatorBuilder extends ActuatorBuilder<MotionActuator> {


    public MotionActuatorBuilder setTargetRigidBody(RigidBody targetRigidBody) {
        brick.targetRigidBody = targetRigidBody;
        return this;

    }

    public MotionActuatorBuilder setVelocity(Vector2 velocity) {
        brick.velocity = velocity;
        return this;

    }

    public MotionActuatorBuilder setForce(Vector2 force) {
        brick.force = force;
        return this;

    }

    public MotionActuatorBuilder setImpulse(Vector2 impulse) {
        brick.impulse = impulse;
        return this;

    }


    public MotionActuatorBuilder setAngularVelocity(float angularVelocity) {
        brick.angularVelocity = angularVelocity;
        return this;

    }


    public MotionActuatorBuilder setTorque(float torque) {
        brick.torque = torque;
        return this;

    }


    public MotionActuatorBuilder setAngularImpulse(float angularImpulse) {
        brick.angularImpulse = angularImpulse;
        return this;

    }


    public MotionActuatorBuilder setFixedRotation(boolean fixedRotation) {
        brick.fixedRotation = fixedRotation;
        return this;

    }


    public MotionActuatorBuilder setLimitVelocityX(float limitVelocityX) {
        brick.limitVelocityX = limitVelocityX;
        return this;

    }


    public MotionActuatorBuilder setLimitVelocityY(float limitVelocityY) {
        brick.limitVelocityY = limitVelocityY;
        return this;

    }


}
