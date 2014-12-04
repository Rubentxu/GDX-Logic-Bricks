package com.indignado.logicbricks.core;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public interface Script {

    public void execute(ObjectMap<String,Sensor> sensors, ObjectMap<String,Actuator> actuators);

}
