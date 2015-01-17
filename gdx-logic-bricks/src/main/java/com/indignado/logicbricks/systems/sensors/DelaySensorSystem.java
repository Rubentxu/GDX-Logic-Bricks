package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.components.sensors.DelaySensorComponent;
import com.indignado.logicbricks.core.sensors.DelaySensor;
import com.indignado.logicbricks.utils.Log;

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
        if (sensor.time != -1) sensor.time += deltaTime;

        if (sensor.time >= sensor.delay) {
            if (sensor.positive && sensor.time >= (sensor.delay + sensor.duration)) {
                if (sensor.repeat) {
                    sensor.time = 0;
                } else {
                    sensor.time = -1;
                }
            } else {
                Log.debug(tag, "query is true time %f", sensor.time);
                isActive = true;
            }

        }
        return isActive;

    }


}
