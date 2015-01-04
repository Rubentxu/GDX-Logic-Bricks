package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.AlwaysSensor;

/**
 * @author Rubentxu.
 */
public class AlwaysSensorBuilder extends SensorBuilder<AlwaysSensor> {

    public AlwaysSensorBuilder() {
        brick = new AlwaysSensor();

    }


    @Override
    public AlwaysSensor getBrick() {
        AlwaysSensor brickTemp = brick;
        brick = new AlwaysSensor();
        return brickTemp;

    }

}
