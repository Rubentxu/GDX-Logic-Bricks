package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.DelaySensor;

/**
 * @author Rubentxu.
 */
public class DelaySensorBuilder extends SensorBuilder<DelaySensor> {

    public DelaySensorBuilder() {
        brick = new DelaySensor();

    }


    public DelaySensorBuilder setDelay(float delay) {
        brick.delay = delay;
        return this;

    }


    public DelaySensorBuilder setDuration(float duration) {
        brick.duration = duration;
        return this;

    }


    public DelaySensorBuilder setRepeat(boolean repeat) {
        brick.repeat =  repeat;
        return this;

    }


    @Override
    public DelaySensor getBrick() {
        DelaySensor brickTemp = brick;
        brick = new DelaySensor();
        return brickTemp;

    }

}
