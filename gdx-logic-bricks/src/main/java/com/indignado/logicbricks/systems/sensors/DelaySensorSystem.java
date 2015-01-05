package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.components.sensors.AlwaysSensorComponent;
import com.indignado.logicbricks.components.sensors.DelaySensorComponent;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.DelaySensor;

/**
 * @author Rubentxu
 */
public class DelaySensorSystem extends SensorSystem<DelaySensor, DelaySensorComponent> {


    public DelaySensorSystem() {
        super(DelaySensorComponent.class);

    }


    @Override
    public boolean query(DelaySensor sensor, float deltaTime) {
        boolean isActive = false;
        if(sensor.time != -1) sensor.time += deltaTime;

        if(sensor.time >= sensor.delay) {
            if(sensor.positive &&  sensor.time >= (sensor.delay + sensor.duration)) {
                if(sensor.repeat) {
                    sensor.time = 0;
                } else {
                    sensor.time = -1;
                }
            } else {
                isActive = true;
            }

        }
        return isActive;

    }


}
