package com.indignado.logicbricks.utils.box2d;

import com.indignado.logicbricks.bricks.LogicBricks;
import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.sensors.Sensor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBricksBuilder {
    private LogicBricks logicBricks;


    public LogicBricksBuilder(String name) {
        this.logicBricks = new LogicBricks();
        this.logicBricks.name = name;

    }


    public LogicBricksBuilder addSensor(Sensor sensor) {
        Set<Sensor> sensorsList = logicBricks.sensors.get(sensor.getClass());
        if (sensorsList == null) {
            sensorsList = new HashSet<Sensor>();
            logicBricks.sensors.put(sensor.getClass(), sensorsList);

        }
        sensorsList.add(sensor);
        return this;

    }


    public LogicBricksBuilder addController(Controller controller) {
        Set<Controller> controllerList = logicBricks.controllers.get(controller.getClass());
        if (controllerList == null) {
            controllerList = new HashSet<Controller>();
            logicBricks.controllers.put(controller.getClass(), controllerList);

        }
        controllerList.add(controller);
        return this;

    }


    public LogicBricksBuilder addActuator(Actuator actuator) {
        Set<Actuator> actuatorList = logicBricks.actuators.get(actuator.getClass());
        if (actuatorList == null) {
            actuatorList = new HashSet<Actuator>();
            logicBricks.actuators.put(actuator.getClass(), actuatorList);

        }
        actuatorList.add(actuator);
        return this;

    }


    public LogicBricks build() {
        return logicBricks;

    }

}