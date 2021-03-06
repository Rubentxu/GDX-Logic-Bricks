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

    public float angularVelocity = 0.0f;
    public float torque = 0.0f;
    public float angularImpulse = 0.0f;

    public boolean fixedRotation = false;
    public float limitVelocityX = 0;
    public float limitVelocityY = 0;


    @Override
    public void reset() {
        super.reset();
        targetRigidBody = null;
        velocity = null;
        force = null;
        impulse = null;
        angularVelocity = 0;
        torque = 0;
        angularImpulse = 0;
        fixedRotation = false;
        limitVelocityX = 0;
        limitVelocityY = 0;

    }

}
