package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * @author Rubentxu.
 */
public class BuoyancyComponent extends Component implements Poolable {

    public Vector2 normal = new Vector2(0, 1.0f);
    public float offset = 0.0f;
    public float density = 2.0f;
    public Vector2 velocity = new Vector2(0, 1);
    public float linearDrag = 5.0f;
    public float angularDrag = 1.0f;
    public boolean useDensity = true;
    public Vector2 gravity = new Vector2(0, -10.0f);

    public Array<Body> bodyList = new Array<Body>();


    @Override
    public void reset() {
        normal.set(0, 1f);
        offset = 0.0f;
        density = 2.0f;
        velocity.set(0, 1);
        linearDrag = 5.0f;
        angularDrag = 1.0f;
        useDensity = true;
        gravity.set(0, -10.0f);
        bodyList.clear();

    }

}
