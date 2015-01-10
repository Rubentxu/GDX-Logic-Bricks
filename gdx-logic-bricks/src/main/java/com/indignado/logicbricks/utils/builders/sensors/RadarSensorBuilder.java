package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.AlwaysSensor;

/**
 * @author Rubentxu.
 */
public class RadarSensorBuilder extends SensorBuilder<AlwaysSensor> {

    public RadarSensorBuilder() {
        brick = new AlwaysSensor();

    }


    @Override
    public AlwaysSensor getBrick() {
        AlwaysSensor brickTemp = brick;
        brick = new AlwaysSensor();
        return brickTemp;

    }

}
