package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;

/**
 * @author Rubentxu.
 */
public class FrictionJointBuilder extends BaseJointBuilder<FrictionJointDef> {


    public FrictionJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new FrictionJointDef();

    }


    public FrictionJointBuilder initialize (Body bodyA, Body bodyB, Vector2 anchor) {
        jointDef.initialize(bodyA, bodyB, anchor);
        return this;

    }


    public FrictionJointBuilder maxForce(float maxForce) {
        jointDef.maxForce = maxForce;
        return this;

    }


    public FrictionJointBuilder maxTorque(float maxTorque) {
        jointDef.maxTorque = maxTorque;
        return this;

    }


}