package com.indignado.logicbricks.components.sensors;

import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;

import java.util.Set;

/**
 * Created on 05/11/14.
 *
 * @author Rubentxu
 */
public class MouseSensorComponent extends SensorComponent {
    public IntMap<Set<MouseSensor>> mouseSensors = new IntMap<Set<MouseSensor>>();

}
