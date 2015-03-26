package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Rubentxu.
 */
public class TextureView extends View {
    public TextureRegion textureRegion;
    public boolean flipX = false;
    public boolean flipY = false;


    public TextureView() {
        super.transform = new Transform2D();

    }


    public TextureView(RectTransform transform) {
        super.transform = transform;

    }


    public TextureView setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
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
        flipX = false;
        flipY = false;

    }


}
