package com.indignado.logicbricks.core;

import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public interface Script {

    public void execute(Array<Sensor> sensors);

}
