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
    public boolean query(TimerSensor sensor, float deltaTime) {
        boolean isActive = false;
        if (sensor.time == -1) return isPositive(sensor);
        if (sensor.duration == TimerSensor.ALWAYS) {
            isActive = true;
        }

        sensor.time += deltaTime;
        if (sensor.delay != 0 && sensor.time >= sensor.delay) {
            sensor.delay = 0;
            sensor.time = 0;

        }

        if (sensor.delay == 0 && sensor.time > sensor.duration) {
            isActive = true;
            if (sensor.repeat) sensor.time = 0;
            else sensor.time = -1;
        }
        return isActive;

    }

}
