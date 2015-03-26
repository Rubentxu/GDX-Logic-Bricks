package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.math.Matrix4;

/**
 * @author Rubentxu.
 */
public abstract class Transform <T extends RigidBody> implements Data {
    public Group group;
    public Matrix4 matrix = new Matrix4();
    public T rigidBody;

    @Override
    public void reset() {
        group = null;
        matrix.idt();
        rigidBody = null;

    }

}
