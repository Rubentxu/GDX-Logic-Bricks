package com.indignado.games.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;

import java.util.Map;

/**
 * @author Rubentxu.
 */
public class View {
    public String name;
    public Body attachedRigidbody;
    public Map<String,Animation> animations;
    public TextureRegion textureRegion;
    public int height;
    public int width;
    public int opacity;
    public boolean flip;
    public int layer;
    public Color tint;

}
