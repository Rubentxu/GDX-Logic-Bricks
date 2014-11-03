package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author Rubentxu.
 */
public class CameraActuator extends Actuator {
    public OrthographicCamera camera;
    public Entity target;
    public short min;
    public short max;
    public short height;

    // public short damping;



}
