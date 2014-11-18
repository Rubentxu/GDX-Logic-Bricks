package com.indignado.logicbricks.data;

import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.bricks.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public interface Script {

    public void execute(Array<Sensor> sensors);

}
