package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.View;


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
        ViewsComponent viewsComponent = target.getComponent(ViewsComponent.class);
        if(viewsComponent == null) return false;

        Rectangle rectangle = new Rectangle();
        for(View view : viewsComponent.views){
            rectangle.set(view.transform.getPosition().x, view.transform.getPosition().y , view.width, view.height);
            if(rectangle.contains(posX,posY)) return true;
        }
        return false;

    }

}