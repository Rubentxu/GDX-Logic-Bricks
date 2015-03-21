package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.graphics.Camera;
import com.indignado.logicbricks.core.actuators.CameraActuator;

/**
 * @author Rubentxu.
 */
public class CameraActuatorBuilder extends ActuatorBuilder<CameraActuator> {


    public CameraActuatorBuilder() {
        brick = new CameraActuator();

    }

    public CameraActuatorBuilder setCamera(Camera camera) {
        brick.camera = camera;
        return this;

    }


    public CameraActuatorBuilder setFollowTagEntity(String tag) {
        brick.followTagEntity = tag;
        return this;

    }


    public CameraActuatorBuilder setDamping(float damping) {
        brick.damping = damping;
        return this;

    }


    public CameraActuatorBuilder setHeight(short height) {
        brick.height = height;
        return this;

    }

    @Override
    public CameraActuator getBrick() {
        CameraActuator brickTemp = brick;
        brick = new CameraActuator();
        return brickTemp;

    }
}
