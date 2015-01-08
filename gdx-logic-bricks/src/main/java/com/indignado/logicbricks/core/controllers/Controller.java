package com.indignado.logicbricks.core.controllers;

import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class Controller extends LogicBrick {
    public ObjectMap<String, Sensor> sensors = new ObjectMap<String, Sensor>();
    public ObjectMap<String, Actuator> actuators = new ObjectMap<String, Actuator>();

    @Override
    public void reset() {
        sensors.clear();
        actuators.clear();

    }

}
