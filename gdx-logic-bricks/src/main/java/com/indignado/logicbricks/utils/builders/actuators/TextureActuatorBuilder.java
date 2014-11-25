package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.graphics.Color;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.actuators.TextureActuator;

/**
 * @author Rubentxu.
 */
public class TextureActuatorBuilder extends ActuatorBuilder<TextureActuator> {


    public TextureActuatorBuilder setTextureView(TextureView textureView) {
        actuator.textureView = textureView;
        return this;

    }


    public TextureActuatorBuilder setHeight(float height) {
        actuator.height = height;
        return this;

    }


    public TextureActuatorBuilder setWidth(float width) {
        actuator.width = width;
        return this;

    }


    public TextureActuatorBuilder setOpacity(int opacity) {
        actuator.opacity = opacity;
        return this;

    }


    public TextureActuatorBuilder setFlipX(Boolean flipX) {
        actuator.flipX = flipX;
        return this;

    }


    public TextureActuatorBuilder setFlipY(Boolean flipY) {
        actuator.flipY = flipY;
        return this;

    }


    public TextureActuatorBuilder setTint(Color tint) {
        actuator.tint = tint;
        return this;

    }


}
