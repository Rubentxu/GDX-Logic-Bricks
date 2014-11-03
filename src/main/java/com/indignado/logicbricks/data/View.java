package com.indignado.logicbricks.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Transform;

import java.util.Map;

/**
 * @author Rubentxu.
 */
public class View {
    public String name;
    public Transform transform;
    public Map<String,Animation> animations;
    public TextureRegion textureRegion;
    public int height;
    public int width;
    public int opacity = 1;
    public boolean flipX;
    public boolean flipY;
    public int layer;
    public Color tint;

}
