package com.indignado.logicbricks.utils.logicbricks;

import com.indignado.logicbricks.bricks.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class SensorsBuilder<S extends Sensor> {
    private S sensorDef;


    private void reset(boolean dispose) {
        sensorDef = null;

    }


}
