package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.components.sensors.AlwaysSensorComponent;

/**
 * @author Rubentxu
 */
public class AlwaysSensorSystem extends SensorSystem<AlwaysSensor, AlwaysSensorComponent> {


    public AlwaysSensorSystem() {
        super(AlwaysSensorComponent.class);

    }


    @Override
    public void processSensor(AlwaysSensor sensor) {
        sensor.pulseSignal = true;

    }


    @Override
    public void clearSensor() {

    }

}
