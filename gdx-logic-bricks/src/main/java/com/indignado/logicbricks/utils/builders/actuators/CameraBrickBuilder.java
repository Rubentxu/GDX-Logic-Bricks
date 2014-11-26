package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.indignado.logicbricks.core.actuators.CameraActuator;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class CameraBrickBuilder extends ActuatorBuilder<CameraActuator> {


    public CameraBrickBuilder setCamera(OrthographicCamera camera) {
        brick.camera = camera;
        return this;

    }


    public CameraBrickBuilder setTarget(Entity target) {
        brick.target = target;
        return this;

    }


    public CameraBrickBuilder setHeight(short height) {
        brick.height = height;
        return this;

    }

}
