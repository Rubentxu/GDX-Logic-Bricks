package com.indignado.logicbricks.utils.builders.joints;

/**
 * @author Rubentxu.
 */
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BaseJointBuilder<T extends JointDef> {
    protected T jointDef;
    protected World world;

    public BaseJointBuilder(World world) {
        this.world = world;

    }


    public  <JB extends BaseJointBuilder> JB bodyA(Body bodyA) {
        jointDef.bodyA = bodyA;
        return (JB) this;

    }


    public <JB extends BaseJointBuilder> JB bodyB(Body bodyB) {
        jointDef.bodyB = bodyB;
        return (JB) this;

    }


    public <JB extends BaseJointBuilder> JB collideConnected(boolean collideConnected) {
        jointDef.collideConnected = collideConnected;
        return (JB) this;

    }


    protected Joint build() {
        return world.createJoint(jointDef);

    }

    public abstract void reset();

}