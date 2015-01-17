package com.indignado.logicbricks.core.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;


/**
 * @author Rubentxu
 */
public class MouseSensor extends Sensor {
    // Config Values
    public MouseEvent mouseEvent;
    public Entity target;

    // Signal Values
    public MouseEvent mouseEventSignal;
    public Vector2 positionSignal = new Vector2();
    public int amountScrollSignal = 0;

    @Override
    public void reset() {
        super.reset();
        mouseEvent = null;
        target = null;
        positionSignal.set(0, 0);
        amountScrollSignal = 0;
        mouseEventSignal = null;

    }


    public enum MouseEvent {
        MOUSE_OVER, MOVEMENT, WHEEL_DOWN, WHEEL_UP, RIGHT_BUTTON_DOWN,
        MIDDLE_BUTTON_DOWN, LEFT_BUTTON_DOWN, RIGHT_BUTTON_UP,
        MIDDLE_BUTTON_UP, LEFT_BUTTON_UP
    }

}
