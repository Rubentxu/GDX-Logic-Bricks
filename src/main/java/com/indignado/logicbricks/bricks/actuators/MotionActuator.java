package com.indignado.logicbricks.bricks.actuators;

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
    // public boolean isLocal = false;



}