package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.CollisionSensor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created on 05/11/14.
 * @author Rubentxu
 */
public class CollisionSensorComponent extends Component {
    public Map<String,Set<CollisionSensor>> collisionSensors = new HashMap<String,Set<CollisionSensor>>();

}
