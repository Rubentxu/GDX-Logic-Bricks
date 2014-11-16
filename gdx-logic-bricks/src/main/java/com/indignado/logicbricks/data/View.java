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
    public Transform attachedTransform;
    public Vector2 position = new Vector2();
    public Vector2 localPosition;
    public float rotation = 0;
    public int opacity = 1;
    public int layer;
    public Color tint;

}
