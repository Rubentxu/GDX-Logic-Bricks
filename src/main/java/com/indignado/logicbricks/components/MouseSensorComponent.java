package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created on 05/11/14.
 * @author Rubentxu
 */
public class MouseSensorComponent extends Component {
    public Map<String,Set<MouseSensor>> mouseSensors = new HashMap<String,Set<MouseSensor>>();

}
