package com.indignado.games.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Transform;

/**
 * @author Rubentxu.
 */
public class Image {
    public String name;
    public Transform transform;
    public Animation animation;
    public TextureRegion textureRegion;
    public int scaleHeight;
    public int scaleWidth;
    public int opacity;
    public boolean flip;
    public int layer;
    public Color tint;

}
