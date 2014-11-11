package com.indignado.logicbricks.systems.sensors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.components.sensors.KeyboardSensorComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorSystem extends SensorSystem<KeyboardSensor, KeyboardSensorComponent> implements InputProcessor {
    private Set<KeyboardSensor> keyboardSensors;


    public KeyboardSensorSystem() {
        super(KeyboardSensorComponent.class);
        keyboardSensors = new HashSet<KeyboardSensor>();

    }


    @Override
    public void processSensor(KeyboardSensor sensor) {
        boolean isActive = false;
        keyboardSensors.add(sensor);
        sensor.keysSignal.clear();

        if (sensor.keyCode != Input.Keys.UNKNOWN) {
            if (sensor.keysCodeSignal.contains(sensor.keyCode)) {
                //Gdx.app.log("KeyboardSensorSystem", "sensor keyCodeSignal contains: " + sensor.keyCode);
                isActive = true;
            }
        } else if (sensor.allKeys) {
            isActive = true;
            //Gdx.app.log("KeyboardSensorSystem", "sensor allKeys: ");
            if (sensor.logToggle) {
                for (Character key : sensor.keysSignal) {
                    sensor.target += key;
                }
            }
        }
        sensor.pulseSignal = isActive;

    }

    @Override
    public void clearSensor() {
        keyboardSensors.clear();
    }


    @Override
    public boolean keyDown(int keycode) {
        //Gdx.app.log("KeyboardSensorSystem", "sensor keyDown event: " + keycode);
        for (KeyboardSensor ks : keyboardSensors) {
            ks.keysCodeSignal.add(new Integer(keycode));
            //Gdx.app.log("KeyboardSensorSystem", "key size: " + ks.keysCodeSignal.size());
        }
        return false;

    }


    @Override
    public boolean keyUp(int keycode) {
        //Gdx.app.log("KeyboardSensorSystem", "sensor keyUp event: " + keycode);
        for (KeyboardSensor ks : keyboardSensors) {
            ks.keysCodeSignal.remove(new Integer(keycode));
            //Gdx.app.log("KeyboardSensorSystem", "key size: " + ks.keysCodeSignal.size());

        }
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
