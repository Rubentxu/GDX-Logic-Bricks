package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.sensors.MouseSensorComponent;
import com.indignado.logicbricks.data.View;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu
 */
public class MouseSensorSystem extends SensorSystem<MouseSensor, MouseSensorComponent> implements InputProcessor {
    private Set<MouseSensor> mouseSensors;

    public MouseSensorSystem() {
        super(MouseSensorComponent.class);
        mouseSensors = new HashSet<MouseSensor>();

    }


    @Override
    public void processSensor(MouseSensor sensor) {
        boolean isActive = false;
        this.mouseSensors.add(sensor);

        if (sensor.mouseEventSignal) {
            switch (sensor.mouseEvent) {
                case MOUSE_OVER:
                    sensor.mouseEventSignal = false;
                    if (sensor.target == null) isActive = false;
                    isActive = isMouseOver(sensor.target, sensor.positionXsignal, sensor.positionYsignal);
                    break;
                case MOVEMENT:
                case WHEEL_DOWN:
                case WHEEL_UP:
                default:
                    sensor.mouseEventSignal = false;
                    isActive = true;
            }
        }
        sensor.pulseSignal = isActive;

    }


    @Override
    public void clearSensor() {
        mouseSensors.clear();

    }


    public boolean isMouseOver(Entity target, int posX, int posY) {
        ViewsComponent viewsComponent = target.getComponent(ViewsComponent.class);
        if (viewsComponent == null) return false;

        Rectangle rectangle = new Rectangle();
        for (View view : viewsComponent.views) {
            rectangle.set(view.transform.getPosition().x - view.width / 2, view.transform.getPosition().y - view.height / 2,
                    view.width, view.height);
            if (rectangle.contains(posX, posY)) return true;
        }
        return false;

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