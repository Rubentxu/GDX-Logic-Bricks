package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author Rubentxu.
 */
public class CameraActuator extends Actuator {
    public OrthographicCamera camera;
    public Entity target;
    public float min = 0;
    public float max = 0;
    public short height = 0;
    public boolean fixedXaxis = false;
    public boolean fixedYaxis = false;
    public boolean checkMove = false;


    // public short damping;


}
