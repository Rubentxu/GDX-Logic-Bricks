package com.indignado.logicbricks.core.actuators;

import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.data.RigidBody;

/**
 * @author Rubentxu.
 */
public class MotionActuator extends Actuator {
    public RigidBody targetRigidBody;
    public Vector2 velocity;
    public Vector2 force;
    public Vector2 impulse;

    public float angularVelocity = 0;
    public float torque = 0;
    public float angularImpulse = 0;

    public boolean fixedRotation = false;
    public float limitVelocityX = 0;
    public float limitVelocityY = 0;


}
