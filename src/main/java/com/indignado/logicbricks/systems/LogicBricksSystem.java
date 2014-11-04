package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.bricks.LogicBricks;
import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.LogicBricksComponent;
import com.indignado.logicbricks.components.StateComponent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public abstract class LogicBricksSystem extends IteratingSystem {
    private ComponentMapper<LogicBricksComponent> logicBricksMapper;
    private ComponentMapper<StateComponent> stateMapper;


    public LogicBricksSystem() {
        super(Family.getFor(LogicBricksComponent.class, StateComponent.class), 0);
        logicBricksMapper = ComponentMapper.getFor(LogicBricksComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    private boolean validLogicBricks(Entity entity, String state) {
        return logicBricksMapper.get(entity) != null && logicBricksMapper.get(entity).logicBricks.containsKey(state)
                && logicBricksMapper.get(entity).logicBricks.get(state) != null;

    }


    public <T extends Sensor> Set<T> getSensors(Class<T> clazz, Entity entity) {
        Set<T> sensorTemp = new HashSet<T>();
        String state = stateMapper.get(entity).get();
        if (validLogicBricks(entity, state)) {
            for (LogicBricks brick : logicBricksMapper.get(entity).logicBricks.get(state)) {
                if (brick.sensors.containsKey(clazz)) {
                    Set<T> sensors = (Set<T>) brick.sensors.get(clazz);
                    for (T s : sensors) {
                        if (!s.isTap() && !sensorTemp.contains(s)) {
                            sensorTemp.add(s);

                        }
                    }
                }
            }
        }
        return sensorTemp;

    }


    public <T extends Controller> Set<T> getControllers(Class<T> clazz, Entity entity) {
        Set<T> controllerTemp = new HashSet<T>();
        String state = stateMapper.get(entity).get();
        if (validLogicBricks(entity, state)) {
            for (LogicBricks brick : logicBricksMapper.get(entity).logicBricks.get(state)) {
                if (brick.controllers.containsKey(clazz)) {
                    Set<T> controllers = (Set<T>) brick.controllers.get(clazz);
                    for (T c : controllers) {
                        if (!controllerTemp.contains(c)) {
                            controllerTemp.add(c);

                        }
                    }
                }
            }
        }
        return controllerTemp;

    }


    public <T extends Actuator> Set<T> getActuators(Class<T> clazz, Entity entity) throws LogicBricksException {
        Set<T> actuatorTemp = new HashSet<T>();
        String state = stateMapper.get(entity).get();

        if (validLogicBricks(entity, state)) {
            for (LogicBricks brick : logicBricksMapper.get(entity).logicBricks.get(state)) {
                if (brick.actuators.containsKey(clazz)) {
                    Set<T> actuators = (Set<T>) brick.actuators.get(clazz);
                    for (T a : actuators) {
                        Iterator<Controller> it = a.controllers.iterator();
                        if(!it.hasNext()) throw new LogicBricksException("LogicBricksSystem", "This actuator does not have any associated controller");
                        boolean activeActuator = true;
                        while (it.hasNext()) {
                            if (!it.next().pulseSignal) activeActuator = false;

                        }
                        if (activeActuator) {
                            actuatorTemp.add(a);

                        }
                    }
                }
            }
        }
        return actuatorTemp;

    }


}
