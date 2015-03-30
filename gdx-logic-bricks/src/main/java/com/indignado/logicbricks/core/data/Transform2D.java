package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Rubentxu.
 */
public class Transform2D extends Transform<RigidBody2D> {

    public Rectangle bounds = new Rectangle();
    public Vector2 pivot = new Vector2();

    public Transform2D() {
        bounds.set(x, y, scaleX, scaleY);
        bounds.getCenter(pivot);

    }


    @Override
    public void reset() {
        super.reset();
        bounds.set(x,y,scaleX,scaleY);
        bounds.getCenter(pivot);

    }

}
