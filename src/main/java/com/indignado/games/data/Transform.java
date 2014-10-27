package com.indignado.games.data;

import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * @author Rubentxu.
 */
public class Transform extends com.badlogic.gdx.physics.box2d.Transform {
    public Vector2 position  = getPosition();
    public Vector2 scale = new Vector2(1.0f, 1.0f);
    public Vector2 origin = new Vector2();
    public float rotation = getRotation();

}
