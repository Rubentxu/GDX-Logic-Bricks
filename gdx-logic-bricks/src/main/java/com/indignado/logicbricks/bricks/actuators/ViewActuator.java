package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.gdx.graphics.Color;
import com.indignado.logicbricks.data.Property;
import com.indignado.logicbricks.data.View;

/**
 * @author Rubentxu.
 */
public class ViewActuator extends Actuator {
    public View targetView;
    public float height = 0;
    public float width = 0;
    public int opacity = -1;
    public Boolean flipX;
    public Boolean flipY;
    public Color tint;

}
