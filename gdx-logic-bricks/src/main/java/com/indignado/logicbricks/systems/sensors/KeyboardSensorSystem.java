package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.components.sensors.KeyboardSensorComponent;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorSystem extends SensorSystem<KeyboardSensor, KeyboardSensorComponent> implements InputProcessor, EntityListener {
    private Set<KeyboardSensor> keyboardSensors;


    public KeyboardSensorSystem() {
        super(KeyboardSensorComponent.class);
        keyboardSensors = new HashSet<KeyboardSensor>();

    }


    @Override
    public void processSensor(KeyboardSensor sensor, float deltaTime) {
        boolean isActive = false;
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
                    sensor.target = sensor.target + key;
                }
            }
        }
        sensor.pulseSignal = isActive;

    }


    @Override
    public boolean keyDown(int keycode) {
        log.debug("key size: %d",keyboardSensors.size());
        for (KeyboardSensor ks : keyboardSensors) {
            ks.keysCodeSignal.add(new Integer(keycode));

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


    @Override
    public void entityAdded(Entity entity) {
        log.debug("KeyboardSensor add");
        KeyboardSensorComponent keyboardSensorComponent = entity.getComponent(KeyboardSensorComponent.class);
        if (keyboardSensorComponent != null) {
            IntMap<Set<KeyboardSensor>> map = keyboardSensorComponent.sensors;
            log.debug("KeyboardSensor added %d", map.size);
            for (int i = 0; i < map.size; ++i) {
                keyboardSensors.addAll(map.get(i));
            }
        }
    }


    @Override
    public void entityRemoved(Entity entity) {
        KeyboardSensorComponent keyboardSensorComponent = entity.getComponent(KeyboardSensorComponent.class);
        if (keyboardSensorComponent != null) {
            IntMap<Set<KeyboardSensor>> map = keyboardSensorComponent.sensors;
            log.debug("KeyboardSensor remove %d", map.size);
            for (int i = 0; i < map.size; ++i) {
                keyboardSensors.removeAll(map.get(i));
            }
        }
    }

}
