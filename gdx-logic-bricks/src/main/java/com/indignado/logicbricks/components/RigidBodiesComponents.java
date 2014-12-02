package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * @author Rubentxu
 */
public class RigidBodiesComponents extends Component implements Poolable {
    public Array<Body> rigidBodies = new Array<Body>();


    @Override
    public void reset() {
        rigidBodies.clear();

    }

}
