package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.data.RigidBody;

/**
 * @author Rubentxu
 */
public class RigidBodiesComponents extends Component implements Poolable {
    public Array<RigidBody> rigidBodies = new Array<RigidBody>();


    @Override
    public void reset() {
        rigidBodies.clear();

    }

}
