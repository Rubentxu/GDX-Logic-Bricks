package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.graphics.Color;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.actuators.TextureActuator;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class TextureBrickBuilder extends ActuatorBuilder<TextureActuator> {


    public TextureBrickBuilder setTextureView(TextureView textureView) {
        brick.textureView = textureView;
        return this;

    }


    public TextureBrickBuilder setHeight(float height) {
        brick.height = height;
        return this;

    }


    public TextureBrickBuilder setWidth(float width) {
        brick.width = width;
        return this;

    }


    public TextureBrickBuilder setOpacity(int opacity) {
        brick.opacity = opacity;
        return this;

    }


    public TextureBrickBuilder setFlipX(Boolean flipX) {
        brick.flipX = flipX;
        return this;

    }


    public TextureBrickBuilder setFlipY(Boolean flipY) {
        brick.flipY = flipY;
        return this;

    }


    public TextureBrickBuilder setTint(Color tint) {
        brick.tint = tint;
        return this;

    }


}
