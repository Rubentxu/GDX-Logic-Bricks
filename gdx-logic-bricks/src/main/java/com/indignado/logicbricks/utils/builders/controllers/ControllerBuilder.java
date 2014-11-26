package com.indignado.logicbricks.utils.builders.controllers;

import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public abstract class ControllerBuilder<T extends Controller> extends BrickBuilder<T> {

    public ControllerBuilder addSensor(Sensor sensor) {
        brick.sensors.add(sensor);
        return this;

    }


}
