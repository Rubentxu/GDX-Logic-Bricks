package com.indignado.logicbricks.core.actuators;

import com.badlogic.gdx.graphics.Color;
import com.indignado.logicbricks.core.data.TextureView;

/**
 * @author Rubentxu.
 */
public class TextureActuator extends Actuator {
    public TextureView textureView;
    public float height = 0;
    public float width = 0;
    public int opacity = -1;
    public Boolean flipX;
    public Boolean flipY;
    public Color tint;


    @Override
    public void reset() {
        super.reset();
        textureView = null;
        height = 0;
        width = 0;
        opacity = -1;
        flipX = null;
        flipY = null;
        tint = null;

    }

}
