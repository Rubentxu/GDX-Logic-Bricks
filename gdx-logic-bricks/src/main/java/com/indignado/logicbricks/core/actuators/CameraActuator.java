package com.indignado.logicbricks.core.actuators;

import com.badlogic.gdx.graphics.Camera;

/**
 * @author Rubentxu.
 */
public class CameraActuator extends Actuator {
    public Camera camera;
    public short height = 0;

    @Override
    public void reset() {
        super.reset();
        camera = null;
        height = 0;

    }

}
