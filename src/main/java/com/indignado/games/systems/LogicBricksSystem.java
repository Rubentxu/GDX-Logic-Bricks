package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.indignado.games.bricks.LogicBricks;
import com.indignado.games.bricks.actuators.Actuator;
import com.indignado.games.bricks.controllers.Controller;
import com.indignado.games.bricks.sensors.CollisionSensor;
import com.indignado.games.bricks.sensors.KeyboardSensor;
import com.indignado.games.bricks.sensors.MouseSensor;
import com.indignado.games.bricks.sensors.Sensor;
import com.indignado.games.components.LogicBricksComponent;
import com.indignado.games.components.StateComponent;

import java.util.HashSet;
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


    public <T extends Sensor> Set<T> getSensors(Class<T> clazz, Entity entity) {
        Set<T> sensorTemp = new HashSet<T>();
        String state = stateMapper.get(entity).get();

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
        return sensorTemp;

    }


    public <T extends Controller> Set<T> getControllers(Class<T> clazz, Entity entity) {
        Set<T> controllerTemp = new HashSet<T>();
        String state = stateMapper.get(entity).get();

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
        return controllerTemp;

    }


    public <T extends Actuator> Set<T> getActuators(Class<T> clazz, Entity entity) {
        Set<T> actuatorTemp = new HashSet<T>();
        String state = stateMapper.get(entity).get();

        for (LogicBricks brick : logicBricksMapper.get(entity).logicBricks.get(state)) {
            if (brick.actuators.containsKey(clazz)) {
                Set<T> actuators = (Set<T>) brick.actuators.get(clazz);
                for (T a : actuators) {
                    if (!actuatorTemp.contains(a)) {
                        actuatorTemp.add(a);

                    }
                }
            }
        }
        return actuatorTemp;

    }






}
