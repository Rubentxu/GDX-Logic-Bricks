package com.indignado.logicbricks.core.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author Rubentxu.
 */
public class CameraActuator extends Actuator {
    public OrthographicCamera camera;
    public Entity target;
    public short height = 0;


}
