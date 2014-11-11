package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;


/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class MouseSensor extends Sensor {
    // Config Values
    public MouseEvent mouseEvent;
    public Entity target;
    // Signal Values
    public boolean mouseEventSignal = false;
    public int positionXsignal = 0;
    public int positionYsignal = 0;
    public int amountScrollSignal = 0;
    public boolean buttonUP = false;
    public enum MouseEvent {
        MOUSE_OVER, MOVEMENT, WHEEL_DOWN, WHEEL_UP, RIGHT_BUTTON,
        MIDDLE_BUTTON, LEFT_BUTTON
    }


}
