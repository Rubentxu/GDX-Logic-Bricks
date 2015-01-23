package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

/**
 * @author Rubentxu.
 */
public class MouseJointBuilder extends BaseJointBuilder<MouseJointDef> {


    public MouseJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new MouseJointDef();

    }


    public MouseJointBuilder target (float x, float y) {
        jointDef.target.set(x,y);
        return this;

    }


    public MouseJointBuilder maxForce(float maxForce) {
        jointDef.maxForce = maxForce;
        return this;

    }


    public MouseJointBuilder frequencyHz(float frequencyHz) {
        jointDef.frequencyHz = frequencyHz;
        return this;

    }


    public MouseJointBuilder dampingRatio(float dampingRatio) {
        jointDef.dampingRatio = dampingRatio;
        return this;

    }

}