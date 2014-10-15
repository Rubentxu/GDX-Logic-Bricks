package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class MouseSensor extends Sensor {

    enum MouseEvent {
        MOUSE_OVER_ANY, MOUSE_OVER, MOVEMENT, WHEEL_DOWN, WHEEL_UP, RIGHT_BUTTON,
        MIDDLE_BUTTON, LEFT_BUTTON
    }

    // Config Values
    public MouseEvent mouseEvent;

    // Signal Values
    public MouseEvent mouseEventSignal;
    public Entity targetSignal;


    public MouseSensor(Entity owner) {
        super(owner);
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
