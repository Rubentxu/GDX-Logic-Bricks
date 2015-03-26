package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Rubentxu.
 */
public class Transform2D extends Transform<RigidBody2D> {
    public Vector2 rotation = new Vector2();

    @Override
    public void reset() {
        super.reset();
        rotation.setZero();

    }

}
