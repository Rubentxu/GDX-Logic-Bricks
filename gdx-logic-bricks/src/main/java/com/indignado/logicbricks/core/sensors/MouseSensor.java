package com.indignado.logicbricks.core.sensors;

import com.badlogic.ashley.core.Entity;


/**
 * @author Rubentxu
 */
public class MouseSensor extends Sensor {
    // Config Values
    public MouseEvent mouseEvent;
    public Entity target;

    // Signal Values
    public MouseEvent mouseEventSignal;
    public int positionXsignal = 0;
    public int positionYsignal = 0;
    public int amountScrollSignal = 0;

    public enum MouseEvent {
        MOUSE_OVER, MOVEMENT, WHEEL_DOWN, WHEEL_UP, RIGHT_BUTTON_DOWN,
        MIDDLE_BUTTON_DOWN, LEFT_BUTTON_DOWN, RIGHT_BUTTON_UP,
        MIDDLE_BUTTON_UP, LEFT_BUTTON_UP
    }


    @Override
    public void reset() {
        super.reset();

    }

}
