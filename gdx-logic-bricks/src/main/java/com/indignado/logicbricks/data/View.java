package com.indignado.logicbricks.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author Rubentxu.
 */
public class View {
    public String name;
    public Transform transform;
    public Vector2 localPosition;
    public int opacity = 1;
    public int layer;
    public Color tint;

}
