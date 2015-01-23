package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;

/**
 * @author Rubentxu.
 */
public class PrismaticJointBuilder extends BaseJointBuilder<PrismaticJointDef, PrismaticJointBuilder> {


    public PrismaticJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new PrismaticJointDef();

    }


    public PrismaticJointBuilder initialize(Body bodyA, Body bodyB, Vector2 anchor, Vector2 axis) {
        jointDef.initialize(bodyA, bodyB, anchor, axis);
        return this;

    }


    public PrismaticJointBuilder enableLimit(boolean enableLimit) {
        jointDef.enableLimit = enableLimit;
        return this;

    }


    public PrismaticJointBuilder enableMotor(boolean enableMotor) {
        jointDef.enableMotor = enableMotor;
        return this;

    }


    public PrismaticJointBuilder lowerTranslation(float lowerTranslation) {
        jointDef.lowerTranslation = lowerTranslation;
        return this;

    }


    public PrismaticJointBuilder maxMotorForce(float maxMotorForce) {
        jointDef.maxMotorForce = maxMotorForce;
        return this;

    }


    public PrismaticJointBuilder motorSpeed(float motorSpeed) {
        jointDef.motorSpeed = motorSpeed;
        return this;

    }


    public PrismaticJointBuilder referenceAngle(float referenceAngle) {
        jointDef.referenceAngle = referenceAngle;
        return this;

    }


    public PrismaticJointBuilder upperTranslation(float upperTranslation) {
        jointDef.upperTranslation = upperTranslation;
        return this;

    }


}