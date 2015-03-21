package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Rubentxu.
 */
public class TextureView extends View {
    public TextureRegion textureRegion;
    public float height;
    public float width;
    public boolean flipX = false;
    public boolean flipY = false;


    public TextureView setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        return this;

    }


    public TextureView setHeight(float height) {
        this.height = height;
        return this;

    }


    public TextureView setWidth(float width) {
        this.width = width;
        return this;

    }


    public TextureView setFlipX(boolean flipX) {
        this.flipX = flipX;
        return this;

    }


    public TextureView setFlipY(boolean flipY) {
        this.flipY = flipY;
        return this;

    }


    @Override
    public void reset() {
        super.reset();
        textureRegion = null;
        height = 0;
        width = 0;
        flipX = false;
        flipY = false;

    }


}
