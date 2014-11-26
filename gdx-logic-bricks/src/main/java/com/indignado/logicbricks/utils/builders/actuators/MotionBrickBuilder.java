package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.data.RigidBody;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class MotionBrickBuilder extends ActuatorBuilder<MotionActuator> {


    public MotionBrickBuilder setTargetRigidBody(RigidBody targetRigidBody) {
        brick.targetRigidBody = targetRigidBody;
        return this;

    }

    public MotionBrickBuilder setVelocity(Vector2 velocity) {
        brick.velocity = velocity;
        return this;

    }

    public MotionBrickBuilder setForce(Vector2 force) {
        brick.force = force;
        return this;

    }

    public MotionBrickBuilder setImpulse(Vector2 impulse) {
        brick.impulse = impulse;
        return this;

    }


    public MotionBrickBuilder setAngularVelocity(float angularVelocity) {
        brick.angularVelocity = angularVelocity;
        return this;

    }


    public MotionBrickBuilder setTorque(float torque) {
        brick.torque = torque;
        return this;

    }


    public MotionBrickBuilder setAngularImpulse(float angularImpulse) {
        brick.angularImpulse = angularImpulse;
        return this;

    }


    public MotionBrickBuilder setFixedRotation(boolean fixedRotation) {
        brick.fixedRotation = fixedRotation;
        return this;

    }


    public MotionBrickBuilder setLimitVelocityX(float limitVelocityX) {
        brick.limitVelocityX = limitVelocityX;
        return this;

    }


    public MotionBrickBuilder setLimitVelocityY(float limitVelocityY) {
        brick.limitVelocityY = limitVelocityY;
        return this;

    }


}
