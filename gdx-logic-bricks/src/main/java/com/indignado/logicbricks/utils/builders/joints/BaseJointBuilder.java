package com.indignado.logicbricks.utils.builders.joints;

/**
 * @author Rubentxu.
 */
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BaseJointBuilder<T extends JointDef, JB extends BaseJointBuilder> {
    protected T jointDef;
    protected World world;

    public BaseJointBuilder(World world) {
        this.world = world;
        reset();

    }

    protected JB initialize(Body bodyA, Body bodyB) {
        jointDef.bodyA = bodyA;
        jointDef.bodyB = bodyB;
        return (JB) this;

    }


    public JB collideConnected(boolean collideConnected) {
        jointDef.collideConnected = collideConnected;
        return (JB) this;

    }


    public Joint build() {
        return world.createJoint(jointDef);

    }

    public abstract void reset();

}