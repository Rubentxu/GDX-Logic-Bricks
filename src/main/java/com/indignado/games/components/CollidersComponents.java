package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.indignado.games.bricks.sensors.CollisionSensor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 14/10/14.
 *
 * @author Rubentxu
 */
public class CollidersComponents extends Component {
    public Set<CollisionSensor> fixtures = new HashSet<CollisionSensor>();


}
