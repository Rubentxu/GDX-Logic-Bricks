package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class MouseSensor extends Sensor {

    public enum MouseEvent {
        MOUSE_OVER, MOVEMENT, WHEEL_DOWN, WHEEL_UP, RIGHT_BUTTON,
        MIDDLE_BUTTON, LEFT_BUTTON
    }

    // Config Values
    public MouseEvent mouseEvent;
    public Entity target;

    // Signal Values
    public Set<MouseEvent> mouseEventSignal;
    public Entity targetSignal;
    public int positionXsignal;
    public int positionYsignal;
    public int amountScrollSignal;


    public MouseSensor(Entity owner) {
        super(owner);
        mouseEventSignal = new HashSet<>();
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;

        if (mouseEvent.equals(mouseEventSignal)) {
            if (mouseEvent.equals(MouseEvent.MOUSE_OVER)) {
                if (targetSignal.equals(owner)) return true;
                else return false;
            }
            return true;
        }
        return false;

    }

}
