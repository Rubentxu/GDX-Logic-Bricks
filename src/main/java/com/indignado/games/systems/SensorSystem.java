package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.indignado.games.bricks.sensors.DelaySensor;
import com.indignado.games.bricks.sensors.KeyboardSensor;
import com.indignado.games.bricks.sensors.MouseSensor;
import com.indignado.games.bricks.sensors.Sensor;
import com.indignado.games.components.SensorsComponents;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class SensorSystem extends IteratingSystem implements InputProcessor {
    private ComponentMapper<SensorsComponents> sm;
    private Set<KeyboardSensor> keyboardSensors;
    private MouseSensor mouseSensor;


    public SensorSystem() {
        super(Family.getFor(SensorsComponents.class), 0);
        sm = ComponentMapper.getFor(SensorsComponents.class);
        keyboardSensors = new HashSet<>();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        SensorsComponents sc = sm.get(entity);

        for (Sensor sensor : sc.sensors) {
           if(!sensor.isTap()) {
               if(sensor instanceof DelaySensor){
                   DelaySensor delaySensor = (DelaySensor) sensor;
                   delaySensor.deltaTimeSignal = deltaTime;
                   continue;
               }
               if(sensor instanceof KeyboardSensor){
                   keyboardSensors.add((KeyboardSensor) sensor);
                   continue;
               }
               if(sensor instanceof MouseSensor){
                   mouseSensor = (MouseSensor) sensor;
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
        for (KeyboardSensor ks : keyboardSensors) {
            ks.keysSignal.add(character);
        }
        return false;

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(mouseSensor != null) {
            if(button == Input.Buttons.LEFT){
                mouseSensor.mouseEventSignal.add(MouseSensor.MouseEvent.LEFT_BUTTON);
            }
            if(button == Input.Buttons.MIDDLE){
                mouseSensor.mouseEventSignal.add(MouseSensor.MouseEvent.MIDDLE_BUTTON);
            }
            if(button == Input.Buttons.RIGHT){
                mouseSensor.mouseEventSignal.add(MouseSensor.MouseEvent.RIGHT_BUTTON);
            }
        }

        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(mouseSensor != null) {
            if(button == Input.Buttons.LEFT){
                mouseSensor.mouseEventSignal.remove(MouseSensor.MouseEvent.LEFT_BUTTON);
            }
            if(button == Input.Buttons.MIDDLE){
                mouseSensor.mouseEventSignal.remove(MouseSensor.MouseEvent.MIDDLE_BUTTON);
            }
            if(button == Input.Buttons.RIGHT){
                mouseSensor.mouseEventSignal.remove(MouseSensor.MouseEvent.RIGHT_BUTTON);
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
        mouseSensor.mouseEventSignal.add(MouseSensor.MouseEvent.MOVEMENT);
        mouseSensor.positionXsignal= screenX;
        mouseSensor.positionYsignal= screenY;
        return false;

    }


    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
