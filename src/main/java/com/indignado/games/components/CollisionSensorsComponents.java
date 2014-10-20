package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.indignado.games.bricks.sensors.CollisionSensor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 17/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensorsComponents extends Component{
    public Set<CollisionSensor> sensors= new HashSet<CollisionSensor>();

}
