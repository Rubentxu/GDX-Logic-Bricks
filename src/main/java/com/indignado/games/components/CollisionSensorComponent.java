package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.indignado.games.bricks.sensors.CollisionSensor;
import com.indignado.games.bricks.sensors.Sensor;

import java.util.HashSet;
import java.util.Set;

/**
/**
 * @author Rubentxu
 */
public class CollisionSensorComponent extends Component {
    public Set<CollisionSensor> collisionSensor = new HashSet<CollisionSensor>();

}