package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.components.sensors.TimerSensorComponent;
import com.indignado.logicbricks.core.sensors.TimerSensor;

/**
 * @author Rubentxu
 */
public class TimerSensorSystem extends SensorSystem<TimerSensor, TimerSensorComponent> {


    public TimerSensorSystem() {
        super(TimerSensorComponent.class);

    }


    @Override
    public void processSensor(TimerSensor sensor, float deltaTime) {
        if(sensor.time == -1) return;
        if(sensor.duration == TimerSensor.ALWAYS) {
            sensor.pulseSignal = true;
            return;
        }

        sensor.time += deltaTime;

        if(sensor.delay != 0 && sensor.time >= sensor.delay) {
            sensor.delay = 0;
            sensor.time = 0;

        }

        if(sensor.delay == 0 && sensor.time > sensor.duration) {
            sensor.pulseSignal = true;
            if(sensor.repeat) sensor.time = 0;
            else sensor.time = -1;
        }

    }

}
