package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * @author Rubentxu.
 */
public class RevoluteJointBuilder extends BaseJointBuilder<RevoluteJointDef, PulleyJointBuilder> {


    public RevoluteJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new RevoluteJointDef();

    }


    public RevoluteJointBuilder initialize(Body bodyA, Body bodyB, Vector2 anchor) {
        jointDef.initialize(bodyA, bodyB, anchor);
        return this;

    }

    public RevoluteJointBuilder referenceAngle(float referenceAngle) {
        jointDef.referenceAngle = referenceAngle;
        return this;

    }


    public RevoluteJointBuilder enableLimit(boolean enableLimit) {
        jointDef.enableLimit = enableLimit;
        return this;

    }


    public RevoluteJointBuilder lowerAngle(float lowerAngle) {
        jointDef.lowerAngle = lowerAngle;
        return this;

    }


    public RevoluteJointBuilder upperAngle(float upperAngle) {
        jointDef.upperAngle = upperAngle;
        return this;

    }


    public RevoluteJointBuilder enableMotor(boolean enableMotor) {
        jointDef.enableMotor = enableMotor;
        return this;

    }


    public RevoluteJointBuilder motorSpeed(float motorSpeed) {
        jointDef.motorSpeed = motorSpeed;
        return this;

    }


    public RevoluteJointBuilder maxMotorTorque(float maxMotorTorque) {
        jointDef.maxMotorTorque = maxMotorTorque;
        return this;

    }

}