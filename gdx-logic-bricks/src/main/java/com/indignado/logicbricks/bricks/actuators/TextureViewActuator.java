package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.gdx.graphics.Color;
import com.indignado.logicbricks.data.TextureView;
import com.indignado.logicbricks.data.View;

/**
 * @author Rubentxu.
 */
public class TextureViewActuator extends Actuator {
    public TextureView targetView;
    public float height = 0;
    public float width = 0;
    public int opacity = -1;
    public Boolean flipX;
    public Boolean flipY;
    public Color tint;

}
