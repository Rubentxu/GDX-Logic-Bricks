package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.indignado.games.bricks.sensors.KeyboardSensor;
import com.indignado.games.bricks.sensors.MouseSensor;
import com.indignado.games.bricks.sensors.Sensor;
import com.indignado.games.components.sensors.InputSensorsComponents;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class InputSensorsSystem extends IteratingSystem implements InputProcessor {
    private ComponentMapper<InputSensorsComponents> sm;
    private Set<KeyboardSensor> keyboardSensorsListener;
    private Set<MouseSensor> mouseSensorsListener;


    public InputSensorsSystem() {
        super(Family.getFor(InputSensorsComponents.class), 0);
        sm = ComponentMapper.getFor(InputSensorsComponents.class);
        keyboardSensorsListener = new HashSet<KeyboardSensor>();
        mouseSensorsListener = new HashSet<MouseSensor>();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        InputSensorsComponents sc = sm.get(entity);

        for (Sensor sensor : sc.sensors) {
            if (!sensor.isTap()) {
                if (sensor instanceof KeyboardSensor) {
                    if(!keyboardSensorsListener.contains(sensor)) keyboardSensorsListener.add((KeyboardSensor) sensor);
                    continue;
                }
                if (sensor instanceof MouseSensor) {
                    if(!mouseSensorsListener.contains(sensor)) mouseSensorsListener.add((MouseSensor) sensor);
                    continue;
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
        for (KeyboardSensor ks : keyboardSensorsListener) {
            ks.keysSignal.add(character);
        }
        return false;

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (MouseSensor sensor : mouseSensorsListener) {
            switch (button){
                case Input.Buttons.LEFT:
                    if(sensor.mouseEvent.equals(MouseSensor.MouseEvent.LEFT_BUTTON)){
                        sensor.mouseEventSignal = true;
                        sensor.buttonUP = false;
                    }
                    break;
                case Input.Buttons.MIDDLE:
                    if(sensor.mouseEvent.equals(MouseSensor.MouseEvent.MIDDLE_BUTTON)){
                        sensor.mouseEventSignal = true;
                        sensor.buttonUP = false;
                    }
                    break;
                case Input.Buttons.RIGHT:
                    if(sensor.mouseEvent.equals(MouseSensor.MouseEvent.RIGHT_BUTTON)) {
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
        for (MouseSensor sensor : mouseSensorsListener) {
            switch (button){
                case Input.Buttons.LEFT:
                    if(sensor.mouseEvent.equals(MouseSensor.MouseEvent.LEFT_BUTTON)){
                        sensor.mouseEventSignal = false;
                        sensor.buttonUP = true;
                    }
                    break;
                case Input.Buttons.MIDDLE:
                    if(sensor.mouseEvent.equals(MouseSensor.MouseEvent.MIDDLE_BUTTON)){
                        sensor.mouseEventSignal = false;
                        sensor.buttonUP = true;
                    }
                    break;
                case Input.Buttons.RIGHT:
                    if(sensor.mouseEvent.equals(MouseSensor.MouseEvent.RIGHT_BUTTON)) {
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
        for (MouseSensor sensor : mouseSensorsListener) {
            if(sensor.mouseEvent.equals(MouseSensor.MouseEvent.MOVEMENT)) {
                sensor.positionXsignal = screenX;
                sensor.positionYsignal = screenY;
            }
        }
        return false;

    }


    @Override
    public boolean scrolled(int amount) {
        for (MouseSensor sensor : mouseSensorsListener) {
            sensor.mouseEventSignal = false;
            if(sensor.mouseEvent.equals(MouseSensor.MouseEvent.WHEEL_DOWN) && amount < 0){
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
