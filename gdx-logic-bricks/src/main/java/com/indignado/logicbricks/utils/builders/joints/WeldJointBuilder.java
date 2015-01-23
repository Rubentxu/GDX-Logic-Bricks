package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

/**
 * @author Rubentxu.
 */
public class WeldJointBuilder extends BaseJointBuilder<WeldJointDef>{


    public WeldJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new WeldJointDef();

    }


    public WeldJointBuilder initialize (Body body1, Body body2, Vector2 anchor) {
        jointDef.initialize(body1, body2, anchor);
        return this;

    }


    public WeldJointBuilder frequencyHz(float frequencyHz) {
        jointDef.frequencyHz = frequencyHz;
        return this;

    }


    public WeldJointBuilder dampingRatio(float dampingRatio) {
        jointDef.dampingRatio = dampingRatio;
        return this;

    }

}