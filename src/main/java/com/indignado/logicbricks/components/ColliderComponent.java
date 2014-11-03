package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

/**
 * @author Rubentxu.
 */
public class ColliderComponent extends Component{
    public Array<Fixture> fixtures = new Array<Fixture>();

}
