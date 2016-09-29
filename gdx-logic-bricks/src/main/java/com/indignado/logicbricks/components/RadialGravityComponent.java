package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * @author Rubentxu.
 */
public class RadialGravityComponent implements Poolable, Component {

    public float gravity = -9.8f;
    public float radius = 0;
    public float gravityFactor = 4;
    public Body attachedRigidBody;

    public Array<Body> bodyList = new Array<Body>();


    @Override
    public void reset() {
        gravity = -9.8f;
        radius = 0;
        gravityFactor = 0;
        attachedRigidBody = null;
        bodyList.clear();

    }

}
