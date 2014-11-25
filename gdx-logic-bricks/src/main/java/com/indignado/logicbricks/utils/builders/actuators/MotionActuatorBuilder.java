package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.data.RigidBody;
import com.indignado.logicbricks.core.actuators.MotionActuator;

/**
 * @author Rubentxu.
 */
public class MotionActuatorBuilder extends ActuatorBuilder<MotionActuator> {


    public MotionActuatorBuilder setTargetRigidBody(RigidBody targetRigidBody) {
        actuator.targetRigidBody = targetRigidBody;
        return this;

    }

    public MotionActuatorBuilder setVelocity(Vector2 velocity) {
        actuator.velocity = velocity;
        return this;

    }

    public MotionActuatorBuilder setForce(Vector2 force) {
        actuator.force = force;
        return this;

    }

    public MotionActuatorBuilder setImpulse(Vector2 impulse) {
        actuator.impulse = impulse;
        return this;

    }


    public MotionActuatorBuilder setAngularVelocity(float angularVelocity) {
        actuator.angularVelocity = angularVelocity;
        return this;

    }


    public MotionActuatorBuilder setTorque(float torque) {
        actuator.torque = torque;
        return this;

    }


    public MotionActuatorBuilder setAngularImpulse(float angularImpulse) {
        actuator.angularImpulse = angularImpulse;
        return this;

    }


    public MotionActuatorBuilder setFixedRotation(boolean fixedRotation) {
        actuator.fixedRotation = fixedRotation;
        return this;

    }


    public MotionActuatorBuilder setLimitVelocityX(float limitVelocityX) {
        actuator.limitVelocityX = limitVelocityX;
        return this;

    }


    public MotionActuatorBuilder setLimitVelocityY(float limitVelocityY) {
        actuator.limitVelocityY = limitVelocityY;
        return this;

    }


}
