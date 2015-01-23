package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

/**
 * @author Rubentxu.
 */
public class DistanceJointBuilder extends BaseJointBuilder<DistanceJointDef, DistanceJointBuilder> {


    public DistanceJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new DistanceJointDef();

    }


    public DistanceJointBuilder initialize(Body bodyA, Body bodyB, Vector2 anchorA, Vector2 anchorB) {
        jointDef.initialize(bodyA, bodyB, anchorA, anchorB);
        return this;

    }


    public DistanceJointBuilder frequencyHz(float frequencyHz) {
        jointDef.frequencyHz = frequencyHz;
        return this;

    }


    public DistanceJointBuilder dampingRatio(float dampingRatio) {
        jointDef.dampingRatio = dampingRatio;
        return this;

    }


}