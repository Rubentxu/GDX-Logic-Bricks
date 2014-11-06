package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;
import com.indignado.logicbricks.components.KeyboardSensorComponent;
import com.indignado.logicbricks.components.MouseSensorComponent;
import com.indignado.logicbricks.components.StateComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class MouseSensorSystem extends IteratingSystem implements InputProcessor {
    private Set<MouseSensor> mouseSensors;
    private ComponentMapper<MouseSensorComponent> mouseSensorMapper;
    private ComponentMapper<StateComponent> stateMapper;


    public MouseSensorSystem() {
        super(Family.getFor(MouseSensorComponent.class, StateComponent.class), 0);
        mouseSensorMapper = ComponentMapper.getFor(MouseSensorComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        mouseSensors = new HashSet<MouseSensor>();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Set<MouseSensor> mouseSensorsEntity = getMouseSensors(entity);
        if(mouseSensorsEntity != null){
            for (MouseSensor sensor : mouseSensorsEntity) {
                if (!sensor.isTap() && !mouseSensors.contains(sensor)) {
                    mouseSensors.add(sensor);
                }
            }
        }

    }


    private Set<MouseSensor> getMouseSensors(Entity entity) {
        String state = stateMapper.get(entity).get();
        if (mouseSensorMapper.get(entity).mouseSensors.containsKey(state)) {
            return mouseSensorMapper.get(entity).mouseSensors.get(state);
        }
        return null;

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
            if (sensor.mouseEvent.equals(MouseSensor.MouseEvent.MOVEMENT) ||
                    sensor.mouseEvent.equals(MouseSensor.MouseEvent.MOUSE_OVER)) {
                sensor.positionXsignal = screenX;
                sensor.positionYsignal = screenY;
                sensor.mouseEventSignal = true;
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
