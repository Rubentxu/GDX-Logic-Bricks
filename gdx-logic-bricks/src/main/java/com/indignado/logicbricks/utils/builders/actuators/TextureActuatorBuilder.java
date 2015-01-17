package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.graphics.Color;
import com.indignado.logicbricks.core.actuators.TextureActuator;
import com.indignado.logicbricks.core.data.TextureView;

/**
 * @author Rubentxu.
 */
public class TextureActuatorBuilder extends ActuatorBuilder<TextureActuator> {

    public TextureActuatorBuilder() {
        brick = new TextureActuator();

    }


    public TextureActuatorBuilder setTextureView(TextureView textureView) {
        brick.textureView = textureView;
        return this;

    }


    public TextureActuatorBuilder setHeight(float height) {
        brick.height = height;
        return this;

    }


    public TextureActuatorBuilder setWidth(float width) {
        brick.width = width;
        return this;

    }


    public TextureActuatorBuilder setOpacity(int opacity) {
        brick.opacity = opacity;
        return this;

    }


    public TextureActuatorBuilder setFlipX(Boolean flipX) {
        brick.flipX = flipX;
        return this;

    }


    public TextureActuatorBuilder setFlipY(Boolean flipY) {
        brick.flipY = flipY;
        return this;

    }


    public TextureActuatorBuilder setTint(Color tint) {
        brick.tint = tint;
        return this;

    }


    @Override
    public TextureActuator getBrick() {
        TextureActuator brickTemp = brick;
        brick = new TextureActuator();
        return brickTemp;

    }

}
