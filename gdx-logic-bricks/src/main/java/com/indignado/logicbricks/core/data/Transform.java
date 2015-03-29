package com.indignado.logicbricks.core.data;

/**
 * @author Rubentxu.
 */
public abstract class Transform <R extends RigidBody> implements Data {
    public Group group;
    public float x, y, z = 0;
    public float scaleX, scaleY, scaleZ = 1;
    public float yaw, pitch, roll = 0;
    public R rigidBody;



    @Override
    public void reset() {
        group = null;
        rigidBody = null;
        x = y = z = 0;
        scaleX = scaleY = scaleZ = 1;
        yaw = pitch = roll = 0;

    }



}
