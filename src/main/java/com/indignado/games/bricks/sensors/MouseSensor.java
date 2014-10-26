package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.games.components.TextureBoundsComponent;

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
    public boolean mouseEventSignal = false;
    public int positionXsignal = 0;
    public int positionYsignal = 0;
    public int amountScrollSignal = 0;
    public boolean buttonUP = false;


    public MouseSensor(Entity owner) {
        super(owner);

    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;

        if (mouseEventSignal) {
            if(mouseEvent.equals(MouseEvent.MOUSE_OVER)){
                if( target == null ) return false;
                return isMouseOver(target,positionXsignal,positionYsignal);

            }
            return true;
        }

        return false;

    }


    public boolean isMouseOver(Entity target, int posX, int posY) {
        TextureBoundsComponent bounds = target.getComponent(TextureBoundsComponent.class);
        if(bounds == null) return false;
        return bounds.bounds.contains(posX, posY);

    }

}
