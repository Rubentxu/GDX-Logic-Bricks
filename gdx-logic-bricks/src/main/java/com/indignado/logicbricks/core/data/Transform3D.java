package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.math.Quaternion;

/**
 * @author Rubentxu.
 */
public class Transform3D extends Transform<RigidBody3D> {
    public Quaternion rotation = new Quaternion();

    @Override
    public void reset() {
        super.reset();
        rotation.idt();

    }

}
