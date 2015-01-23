package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

/**
 * @author Rubentxu.
 */
public class DistanceJointBuilder extends BaseJointBuilder<DistanceJointDef> {


    public DistanceJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new DistanceJointDef();

    }


    public DistanceJointBuilder length(float length) {
        jointDef.length = length;
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