package com.indignado.logicbricks.components.sensors;

import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;

import java.util.Set;

/**
 * Created on 05/11/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorComponent extends SensorComponent {
    public IntMap<Set<KeyboardSensor>> keyboardSensors = new IntMap<Set<KeyboardSensor>>();

}
