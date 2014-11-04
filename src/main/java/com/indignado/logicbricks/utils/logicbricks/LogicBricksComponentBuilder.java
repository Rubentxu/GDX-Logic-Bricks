package com.indignado.logicbricks.utils.logicbricks;

import com.indignado.logicbricks.bricks.LogicBricks;
import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.LogicBricksComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBricksComponentBuilder {
    private String state;
    private LogicBricksComponent logicBricksComponent;
    private LogicBricksBuilder logicBricksBuilder;


    public LogicBricksComponentBuilder() {
        this.logicBricksComponent = new LogicBricksComponent();

    }


    public LogicBricksComponentBuilder(LogicBricksComponent logicBricksComponent) {
        this.logicBricksComponent = logicBricksComponent;

    }


    public LogicBricksComponentBuilder createLogicBricks(String name, String state) {
        this.state = state;
        Set<LogicBricks> logicBricksList = logicBricksComponent.logicBricks.get(state);
        if (logicBricksList == null) {
            logicBricksList = new HashSet<LogicBricks>();
            logicBricksComponent.logicBricks.put(state, logicBricksList);

        }
        if(this.logicBricksBuilder != null) logicBricksList.add(logicBricksBuilder.build());
        this.logicBricksBuilder = new LogicBricksBuilder(name);
        return this;

    }


    public LogicBricksComponentBuilder addSensor(Sensor sensor) {
        logicBricksBuilder.addSensor(sensor);
        return this;

    }


    public LogicBricksComponentBuilder addController(Controller controller) {
        logicBricksBuilder.addController(controller);
        return this;

    }


    public LogicBricksComponentBuilder addActuator(Actuator actuator) {
        logicBricksBuilder.addActuator(actuator);
        return this;

    }


    public LogicBricksComponent build() {
        Set<LogicBricks> logicBricksList = logicBricksComponent.logicBricks.get(state);
        logicBricksList.add(logicBricksBuilder.build());
        return logicBricksComponent;

    }


}
