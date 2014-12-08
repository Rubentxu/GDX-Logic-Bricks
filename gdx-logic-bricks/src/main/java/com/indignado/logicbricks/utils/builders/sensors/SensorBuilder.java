package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public abstract class SensorBuilder<T extends Sensor> extends BrickBuilder<T> {

    public SensorBuilder<T> setFrequency(float frequency) {
        brick.frequency = frequency;
        return this;

    }


    public SensorBuilder<T> setOnce(boolean once) {
        brick.once = once;
        return this;

    }

}
