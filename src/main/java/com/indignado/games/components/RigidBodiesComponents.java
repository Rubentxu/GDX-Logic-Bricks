package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class RigidBodiesComponents extends Component {
    public Array<Body> rigidBodies = new Array<Body>();

}
