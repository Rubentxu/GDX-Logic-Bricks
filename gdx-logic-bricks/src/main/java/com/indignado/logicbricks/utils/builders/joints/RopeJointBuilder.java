package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

/**
 * @author Rubentxu.
 */
public class RopeJointBuilder extends BaseJointBuilder<RopeJointDef, RopeJointBuilder> {


    public RopeJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new RopeJointDef();

    }


    public RopeJointBuilder localAnchorA(float lx, float ly) {
        jointDef.localAnchorA.set(lx, ly);
        return this;

    }


    public RopeJointBuilder localAnchorB(float lx, float ly) {
        jointDef.localAnchorB.set(lx, ly);
        return this;

    }


    public RopeJointBuilder maxLength(float maxLength) {
        jointDef.maxLength = maxLength;
        return this;

    }

}