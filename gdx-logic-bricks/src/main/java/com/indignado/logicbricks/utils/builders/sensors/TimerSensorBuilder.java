package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.core.sensors.TimerSensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class TimerSensorBuilder extends BrickBuilder<TimerSensor> {

    public TimerSensorBuilder() {
        brick = new TimerSensor();

    }


    public TimerSensorBuilder setRepeat(boolean repeat) {
        brick.repeat = repeat;
        return this;

    }


    public TimerSensorBuilder setDelay(short delay) {
        brick.delay = delay;
        return this;

    }


    public TimerSensorBuilder setDuration(short duration) {
        brick.duration = duration;
        return this;

    }


    @Override
    public TimerSensor getBrick() {
        TimerSensor brickTemp = brick;
        brick = new TimerSensor();
        return brickTemp;

    }

}
