package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.indignado.games.bricks.LogicBricks;
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
public abstract class SensorsSystem extends IteratingSystem {
    private ComponentMapper<LogicBricksComponent> logicBricksMapper;
    private ComponentMapper<StateComponent> stateMapper;


    public SensorsSystem() {
        super(Family.getFor(LogicBricksComponent.class, StateComponent.class), 0);
        logicBricksMapper = ComponentMapper.getFor(LogicBricksComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    public <T extends Sensor> Set<T> getSensors(Class<T> clazz, Entity entity){
        Set<T> sensorTemp = new HashSet<T>();
        String state = stateMapper.get(entity).get();

        for (LogicBricks brick : logicBricksMapper.get(entity).logicBricks.get(state)) {
            if (brick.sensors.containsKey(clazz)) {
                Set<T> sensors = (Set<T>) brick.sensors.get(clazz);
                for (T sensor : sensors) {
                    if (!sensor.isTap() && !sensorTemp.contains(sensor)) {
                        sensorTemp.add(sensor);

                    }
                }
            }
        }
        return sensorTemp;

    }


}
