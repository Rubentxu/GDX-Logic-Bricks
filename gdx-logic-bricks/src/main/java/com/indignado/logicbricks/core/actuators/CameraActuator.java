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


    public CameraActuator setCamera(OrthographicCamera camera) {
        this.camera = camera;
        return this;

    }


    public CameraActuator setTarget(Entity target) {
        this.target = target;
        return this;

    }


    public CameraActuator setHeight(short height) {
        this.height = height;
        return this;

    }

}
