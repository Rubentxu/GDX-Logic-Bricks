package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.data.RigidBody;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class RigidBodiesComponents extends Component {
    public Array<RigidBody> rigidBodies = new Array<>();

}
