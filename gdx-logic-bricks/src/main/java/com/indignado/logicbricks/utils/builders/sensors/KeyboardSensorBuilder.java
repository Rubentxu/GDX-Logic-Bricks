package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class KeyboardSensorBuilder extends SensorBuilder<KeyboardSensor> {

    public KeyboardSensorBuilder() {
        brick = new KeyboardSensor();

    }


    public KeyboardSensorBuilder setKeyCode(int keyCode) {
        brick.keyCode = keyCode;
        return this;

    }


    public KeyboardSensorBuilder setAllKeys(boolean allKeys) {
        brick.allKeys = allKeys;
        return this;

    }


    public KeyboardSensorBuilder setLogToggle(boolean logToggle) {
        brick.logToggle = logToggle;
        return this;

    }


    public KeyboardSensorBuilder setTarget(String target) {
        brick.target = target;
        return this;

    }

    @Override
    public KeyboardSensor getBrick() {
        KeyboardSensor brickTemp = brick;
        brick = new KeyboardSensor();
        return brickTemp;

    }

}
