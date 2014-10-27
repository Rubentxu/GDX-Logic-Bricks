package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.indignado.games.data.Transform;

import java.util.Set;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class TransformComponent extends Component {
    public Array<Transform> transforms = new Array<Transform>();
   

}