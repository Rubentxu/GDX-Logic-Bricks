package com.indignado.logicbricks.utils.builders.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;

/**
 * @author Rubentxu.
 */
public class PulleyJointBuilder extends BaseJointBuilder<PulleyJointDef> {


    public PulleyJointBuilder(World world) {
        super(world);

    }


    @Override
    public void reset() {
        jointDef = new PulleyJointDef();

    }


    public PulleyJointBuilder initialize (Body bodyA, Body bodyB, Vector2 groundAnchorA, Vector2 groundAnchorB, Vector2 anchorA,
                                          Vector2 anchorB, float ratio) {
        jointDef.initialize(bodyA, bodyB, groundAnchorA, groundAnchorB, anchorA, anchorB, ratio);
        return this;

    }

}