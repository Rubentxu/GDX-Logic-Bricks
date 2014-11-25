package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.KeyboardSensor;

/**
 * @author Rubentxu.
 */
public class KeyboardSensorBuilder extends SensorBuilder<KeyboardSensor> {


    public KeyboardSensorBuilder setKeyCode(int keyCode) {
        sensor.keyCode = keyCode;
        return this;

    }


    public KeyboardSensorBuilder setAllKeys(boolean allKeys) {
        sensor.allKeys = allKeys;
        return this;

    }


    public KeyboardSensorBuilder setLogToggle(boolean logToggle) {
        sensor.logToggle = logToggle;
        return this;

    }


    public KeyboardSensorBuilder setTarget(String target) {
        sensor.target = target;
        return this;

    }

}
