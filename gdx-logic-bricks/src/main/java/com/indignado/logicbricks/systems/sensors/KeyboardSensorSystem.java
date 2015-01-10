package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.sensors.KeyboardSensorComponent;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class KeyboardSensorSystem extends SensorSystem<KeyboardSensor, KeyboardSensorComponent> implements InputProcessor, EntityListener {
    private ObjectSet<KeyboardSensor> keyboardSensors;


    public KeyboardSensorSystem() {
        super(KeyboardSensorComponent.class);
        keyboardSensors = new ObjectSet<KeyboardSensor>();

    }


    @Override
    public boolean query(KeyboardSensor sensor, float deltaTime) {
        boolean isActive = false;
        if (sensor.keyCode != Input.Keys.UNKNOWN) {
            if (sensor.keysCodeSignal.contains(sensor.keyCode)) {
                Log.debug(tag, "sensor %s keyCodeSignal contains: %s size signal %d", sensor.name, sensor.keyCode, sensor.keysCodeSignal.size);
                isActive = true;
            }
        } else if (sensor.allKeys && !(sensor.keysSignal.size == 0)) {
            isActive = true;
            Log.debug(tag, "sensor allKeys: signal : %s", sensor.keysCodeSignal);
            if (sensor.logToggle) {
                for (Character key : sensor.keysSignal) {
                    sensor.target = sensor.target + key;
                }
            }
        }
        sensor.keysSignal.clear();
        return isActive;

    }


    @Override
    public boolean keyDown(int keycode) {
        Log.debug(tag, "keyDown size: %d", keyboardSensors.size);
        for (KeyboardSensor ks : keyboardSensors) {
            ks.keysCodeSignal.add(new Integer(keycode));

        }
        Log.debug(tag, "keyDown %d", keycode);
        return false;

    }


    @Override
    public boolean keyUp(int keycode) {
        for (KeyboardSensor ks : keyboardSensors) {
            ks.keysCodeSignal.remove(new Integer(keycode));

        }
        Log.debug(tag, "keyUp %d size %d", keycode, keyboardSensors.first().keysCodeSignal.size);
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
        Log.debug(tag, "KeyboardSensor add");
        KeyboardSensorComponent keyboardSensorComponent = entity.getComponent(KeyboardSensorComponent.class);
        if (keyboardSensorComponent != null) {
            IntMap.Values<ObjectSet<KeyboardSensor>> values = keyboardSensorComponent.sensors.values();
            while (values.hasNext()) {
                keyboardSensors.addAll(values.next());
            }
        }

    }


    @Override
    public void entityRemoved(Entity entity) {
        KeyboardSensorComponent keyboardSensorComponent = entity.getComponent(KeyboardSensorComponent.class);
        if (keyboardSensorComponent != null) {
            IntMap.Values<ObjectSet<KeyboardSensor>> values = keyboardSensorComponent.sensors.values();
            while (values.hasNext())
                for (KeyboardSensor sensor : values.next()) {
                    keyboardSensors.remove(sensor);
                }
        }

    }

}
