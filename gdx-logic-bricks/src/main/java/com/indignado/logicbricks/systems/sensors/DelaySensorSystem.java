package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.components.sensors.DelaySensorComponent;
import com.indignado.logicbricks.core.sensors.DelaySensor;

/**
 * @author Rubentxu
 */
public class DelaySensorSystem extends SensorSystem<DelaySensor, DelaySensorComponent> {


    public DelaySensorSystem() {
        super(DelaySensorComponent.class);

    }


    @Override
    public void processSensor(DelaySensor sensor) {
        if (sensor.timeSignal >= sensor.delay) {
            sensor.timeSignal = 0;
            sensor.pulseSignal = true;
        }
        sensor.pulseSignal = false;

    }


}
