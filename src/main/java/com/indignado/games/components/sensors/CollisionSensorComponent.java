package com.indignado.games.components.sensors;

import com.badlogic.ashley.core.Component;
import com.indignado.games.bricks.sensors.CollisionSensor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
/**
 * @author Rubentxu
 */
public class CollisionSensorComponent extends Component {
    public Map<String,Set<CollisionSensor>> collisionSensors = new HashMap<String,Set<CollisionSensor>>();


}
