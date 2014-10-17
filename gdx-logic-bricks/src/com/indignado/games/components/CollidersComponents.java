package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

/**
 * Created on 14/10/14.
 *
 * @author Rubentxu
 */
public class CollidersComponents extends Component {
    public Array<Fixture> fixtures = new Array<Fixture>();
    public Body attachedRigidbody;


}
