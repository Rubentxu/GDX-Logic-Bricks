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


    public SensorBuilder<T> setInvert(boolean invert) {
        brick.invert = invert;
        return this;

    }


    public SensorBuilder<T> setTap(boolean tap) {
        brick.tap = tap;
        return this;

    }


    public SensorBuilder<T> setPulse(Sensor.Pulse ...pulses) {
        int valuePulse = 0;
        for (int i = 0; i < pulses.length; i++) {
            if(i == 0) valuePulse = pulses[i].getValue();
            else valuePulse |= pulses[i].getValue();
        }
        return setPulse(valuePulse);

    }


    public SensorBuilder<T> setPulse(int pulse) {
        brick.pulse = pulse;
        return this;

    }


}
