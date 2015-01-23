package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;

/**
 * @author Rubentxu.
 */
public class GearJointBuilder extends BaseJointBuilder<GearJointDef> {


    public GearJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new GearJointDef();

    }


    public GearJointBuilder joint1(Joint joint1) {
        jointDef.joint1 = joint1;
        return this;

    }


    public GearJointBuilder joint2(Joint joint2) {
        jointDef.joint2 = joint2;
        return this;

    }


    public GearJointBuilder ratio(float ratio) {
        jointDef.ratio = ratio;
        return this;

    }

}