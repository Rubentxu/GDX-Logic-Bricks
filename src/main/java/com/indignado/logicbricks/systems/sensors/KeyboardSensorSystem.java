package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputProcessor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.KeyboardSensorComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorSystem extends SensorSystem<KeyboardSensor,KeyboardSensorComponent> implements InputProcessor {
    private Set<KeyboardSensor> keyboardSensors;


    public KeyboardSensorSystem() {
        super(KeyboardSensorComponent.class);
        keyboardSensors = new HashSet<KeyboardSensor>();

    }


    @Override
    public void processSensor(KeyboardSensor sensor) {
        boolean isActive = false;
        if(!this.keyboardSensors.contains(sensor)) this.keyboardSensors.add(sensor);

        for (Character ks : sensor.keysSignal) {
            if (sensor.allKeys) {
                if (!sensor.keysSignal.isEmpty()) isActive = true;
                if (sensor.logToggle) {
                    sensor.target += ks;
                }
            } else {
                if (ks.equals(sensor.key)) isActive = true;
            }

        }
        sensor.keysSignal.clear();
        sensor.pulseSignal = isActive;

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
