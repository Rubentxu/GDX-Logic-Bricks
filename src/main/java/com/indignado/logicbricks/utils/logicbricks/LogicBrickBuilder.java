package com.indignado.logicbricks.utils.logicbricks;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.LogicBricks;
import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.LogicBricksComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBrickBuilder {
    private String state;
    private Set<Sensor> sensorSet = new HashSet<>();
    private Set<Controller> controllerSet = new HashSet<>();
    private Set<Actuator> actuatorSet = new HashSet<>();
    private SensorComponent sensorComponent;
    private Entity entity;
    private Sensor sensor;


    public LogicBrickBuilder(Entity entity) {
        this.entity = entity;

    }


    public LogicBrickBuilder addSensor(String state) {
        this.state = state;
        Set<LogicBricks> logicBricksList = logicBricksComponent.logicBricks.get(state);
        if (logicBricksList == null) {
            logicBricksList = new HashSet<LogicBricks>();
            logicBricksComponent.logicBricks.put(state, logicBricksList);

        }
        if (this.logicBricksBuilder != null) logicBricksList.add(logicBricksBuilder.build());
        this.logicBricksBuilder = new LogicBricksBuilder(name);
        return this;

    }


    public LogicBrickBuilder addSensor(Sensor sensor, String state) {
        this.sensor = sensor;
        this.state = state;
        sensorSet.add(sensor);
        return this;

    }


    public LogicBrickBuilder addController(Controller controller) {
        logicBricksBuilder.addController(controller);
        return this;

    }


    public LogicBrickBuilder addActuator(Actuator actuator) {
        logicBricksBuilder.addActuator(actuator);
        return this;

    }


    public LogicBricksComponent build() {
        Set<LogicBricks> logicBricksList = logicBricksComponent.logicBricks.get(state);
        logicBricksList.add(logicBricksBuilder.build());
        return logicBricksComponent;

    }


}
