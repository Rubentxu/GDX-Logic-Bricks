package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.gdx.graphics.Color;
import com.indignado.logicbricks.data.TextureView;

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


    public TextureActuator setTextureView(TextureView textureView) {
        this.textureView = textureView;
        return this;

    }


    public TextureActuator setHeight(float height) {
        this.height = height;
        return this;

    }


    public TextureActuator setWidth(float width) {
        this.width = width;
        return this;

    }


    public TextureActuator setOpacity(int opacity) {
        this.opacity = opacity;
        return this;

    }


    public TextureActuator setFlipX(Boolean flipX) {
        this.flipX = flipX;
        return this;

    }


    public TextureActuator setFlipY(Boolean flipY) {
        this.flipY = flipY;
        return this;

    }

    public TextureActuator setTint(Color tint) {
        this.tint = tint;
        return this;

    }

}
