package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputProcessor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.KeyboardSensorComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorSystem extends IteratingSystem implements InputProcessor {
    private Set<KeyboardSensor> keyboardSensors;
    private ComponentMapper<KeyboardSensorComponent> keyboardSensorMapper;
    private ComponentMapper<StateComponent> stateMapper;


    public KeyboardSensorSystem() {
        super(Family.getFor(KeyboardSensorComponent.class, StateComponent.class), 0);
        keyboardSensorMapper = ComponentMapper.getFor(KeyboardSensorComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        keyboardSensors = new HashSet<KeyboardSensor>();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).get();
        Set<KeyboardSensor> sensors = keyboardSensorMapper.get(entity).sensors.get(state);
        if (sensors != null) {
            for (KeyboardSensor sensor : sensors) {
                if (!sensor.isTap() && !this.keyboardSensors.contains(sensor)) {
                    this.keyboardSensors.add(sensor);
                }
            }
        }

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;

    }


    @Override
    public boolean keyUp(int keycode) {
        return false;

    }


    @Override
    public boolean keyTyped(char character) {
        for (KeyboardSensor ks : keyboardSensors) {
            ks.keysSignal.add(character);
        }
        return false;

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;

    }


    @Override
    public boolean scrolled(int amount) {
        return false;

    }


}
