package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.indignado.logicbricks.core.actuators.CameraActuator;

/**
 * @author Rubentxu.
 */
public class CameraActuatorBuilder extends ActuatorBuilder<CameraActuator> {


    public CameraActuatorBuilder setCamera(OrthographicCamera camera) {
        brick.camera = camera;
        return this;

    }


    public CameraActuatorBuilder setTarget(Entity target) {
        brick.target = target;
        return this;

    }


    public CameraActuatorBuilder setHeight(short height) {
        brick.height = height;
        return this;

    }

}
