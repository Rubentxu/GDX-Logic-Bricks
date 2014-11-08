package com.indignado.logicbricks.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author Rubentxu.
 */
public class View {
    public String name;
    public Transform transform;
    public IntMap<Animation> animations;
    public TextureRegion textureRegion;
    public float height;
    public float width;
    public int opacity = 1;
    public boolean flipX;
    public boolean flipY;
    public int layer;
    public Color tint;

}
