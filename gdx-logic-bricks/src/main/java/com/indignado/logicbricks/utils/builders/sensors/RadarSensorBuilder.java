package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.RadarSensor;

/**
 * @author Rubentxu.
 */
public class RadarSensorBuilder extends SensorBuilder<RadarSensor> {

    public RadarSensorBuilder() {
        brick = new RadarSensor();

    }


    @Override
    public RadarSensor getBrick() {
        RadarSensor brickTemp = brick;
        brick = new RadarSensor();
        return brickTemp;

    }

}
