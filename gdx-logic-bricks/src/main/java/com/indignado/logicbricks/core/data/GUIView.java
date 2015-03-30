package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Rubentxu.
 */
public class GUIView extends View<GUITransform>{
    public TextureRegion textureRegion;
    public boolean flipX = false;
    public boolean flipY = false;

    public GUIView() {
        transform = new GUITransform();
        textureRegion = null;
        flipX = false;
        flipY = false;

    }

    @Override
    public void reset() {
        super.reset();
        textureRegion = null;
        flipX = false;
        flipY = false;

    }

}
