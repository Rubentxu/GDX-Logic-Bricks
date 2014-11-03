package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;
import com.indignado.logicbricks.systems.LogicBricksSystem;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class InputsSensorSystem extends LogicBricksSystem implements InputProcessor {
    private Set<KeyboardSensor> keyboardSensors;
    private Set<MouseSensor> mouseSensors;


    public InputsSensorSystem() {
        super();
        keyboardSensors = new HashSet<KeyboardSensor>();
        mouseSensors = new HashSet<MouseSensor>();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        for (KeyboardSensor sensor : getSensors(KeyboardSensor.class, entity)) {
            if (!sensor.isTap() && !keyboardSensors.contains(sensor)) {
                keyboardSensors.add(sensor);
            }
        }

        for (MouseSensor sensor : getSensors(MouseSensor.class, entity)) {
            if (!sensor.isTap() && !mouseSensors.contains(sensor)) {
                mouseSensors.add(sensor);

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
        for (MouseSensor sensor : mouseSensors) {
            switch (button) {
                case Input.Buttons.LEFT:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.LEFT_BUTTON)) {
                        sensor.mouseEventSignal = true;
                        sensor.buttonUP = false;
                    }
                    break;
                case Input.Buttons.MIDDLE:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.MIDDLE_BUTTON)) {
                        sensor.mouseEventSignal = true;
                        sensor.buttonUP = false;
                    }
                    break;
                case Input.Buttons.RIGHT:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.RIGHT_BUTTON)) {
                        sensor.mouseEventSignal = true;
                        sensor.buttonUP = false;
                    }
                    break;
            }

        }
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (MouseSensor sensor : mouseSensors) {
            switch (button) {
                case Input.Buttons.LEFT:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.LEFT_BUTTON)) {
                        sensor.mouseEventSignal = false;
                        sensor.buttonUP = true;
                    }
                    break;
                case Input.Buttons.MIDDLE:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.MIDDLE_BUTTON)) {
                        sensor.mouseEventSignal = false;
                        sensor.buttonUP = true;
                    }
                    break;
                case Input.Buttons.RIGHT:
                    if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.RIGHT_BUTTON)) {
                        sensor.mouseEventSignal = false;
                        sensor.buttonUP = true;
                    }
                    break;
            }

        }
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (MouseSensor sensor : mouseSensors) {
            if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.MOVEMENT)) {
                sensor.positionXsignal = screenX;
                sensor.positionYsignal = screenY;
            }
        }
        return false;

    }


    @Override
    public boolean scrolled(int amount) {
        for (MouseSensor sensor : mouseSensors) {
            sensor.mouseEventSignal = false;
            if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.WHEEL_DOWN) && amount < 0) {
                sensor.mouseEventSignal = true;
            }
            if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.WHEEL_UP) && amount > 0) {
                sensor.mouseEventSignal = true;
            }
            sensor.amountScrollSignal = amount;
        }

        return false;

    }


}
