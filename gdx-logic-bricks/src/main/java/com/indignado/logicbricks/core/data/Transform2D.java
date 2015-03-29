package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Rubentxu.
 */
public class Transform2D extends Transform<RigidBody2D> {

    public Rectangle bounds = new Rectangle();
    public Vector2 pivot = new Vector2();

    @Override
    public void reset() {
        super.reset();
        bounds.set(x,y,1,1);
        pivot.set(0,0);

    }

}
