package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.components.sensors.RepeatedlySensorComponent;
import com.indignado.logicbricks.core.sensors.RepeatedlySensor;

/**
 * @author Rubentxu
 */
public class RepeatedlySensorSystem extends SensorSystem<RepeatedlySensor, RepeatedlySensorComponent> {


    public RepeatedlySensorSystem() {
        super(RepeatedlySensorComponent.class);

    }


    @Override
    public void processSensor(RepeatedlySensor sensor) {
        if(sensor.limit == RepeatedlySensor.ALWAYS) {
            sensor.pulseSignal = true;
        } else if(sensor.init < sensor.limit) {
            sensor.pulseSignal = true;
            sensor.init++;
        }
    }


}
