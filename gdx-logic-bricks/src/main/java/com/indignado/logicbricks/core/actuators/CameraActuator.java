package com.indignado.logicbricks.core.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;

/**
 * @author Rubentxu.
 */
public class CameraActuator extends Actuator {
    public Camera camera;
    public short height = 0;
    public float damping = 0.08f;
    public String followTagEntity;
    public Entity followEntity;


    @Override
    public void reset() {
        super.reset();
        camera = null;
        height = 0;
        damping = 0;
        followTagEntity = null;
        followEntity = null;

    }

}
