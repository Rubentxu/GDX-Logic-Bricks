package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.indignado.logicbricks.bricks.sensors.CollisionSensor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created on 05/11/14.
 * @author Rubentxu
 */
public class KeyboardSensorComponent extends Component {
    public Map<String,Set<KeyboardSensor>> keyboardSensors = new HashMap<String,Set<KeyboardSensor>>();

}
