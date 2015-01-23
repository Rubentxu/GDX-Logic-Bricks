package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

/**
 * @author Rubentxu.
 */
public class WheelJointBuilder extends BaseJointBuilder<WheelJointDef, WheelJointBuilder> {


    public WheelJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new WheelJointDef();

    }


    public WheelJointBuilder initialize(Body bodyA, Body bodyB, Vector2 anchor, Vector2 axis) {
        jointDef.initialize(bodyA, bodyB, anchor, axis);
        return this;

    }


    public WheelJointBuilder frequencyHz(float frequencyHz) {
        jointDef.frequencyHz = frequencyHz;
        return this;

    }


    public WheelJointBuilder dampingRatio(float dampingRatio) {
        jointDef.dampingRatio = dampingRatio;
        return this;

    }


    public WheelJointBuilder motorSpeed(float motorSpeed) {
        jointDef.motorSpeed = motorSpeed;
        return this;

    }


    public WheelJointBuilder maxMotorTorque(float maxMotorTorque) {
        jointDef.maxMotorTorque = maxMotorTorque;
        return this;

    }

    public WheelJointBuilder enableMotor(boolean enableMotor) {
        jointDef.enableMotor = enableMotor;
        return this;

    }

}