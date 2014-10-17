package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created on 14/10/14.
 *
 * @author Rubentxu
 */
public class CollidersComponent extends Component {
    public IntMap<Fixture> colliders = new IntMap<Fixture>();


}
