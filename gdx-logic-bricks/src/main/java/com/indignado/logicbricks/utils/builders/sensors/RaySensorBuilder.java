package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.core.data.Axis2D;
import com.indignado.logicbricks.core.sensors.RaySensor;

/**
 * @author Rubentxu.
 */
public class RaySensorBuilder extends SensorBuilder<RaySensor> {

    public RaySensorBuilder() {
        brick = new RaySensor();

    }


    public RaySensorBuilder setTargetTag(String tag) {
        brick.targetTag = tag;
        return this;

    }


    public RaySensorBuilder setPropertyName(String name) {
        brick.targetPropertyName = name;
        return this;

    }


    public RaySensorBuilder setAxis(Axis2D axis2D) {
        brick.axis2D = axis2D;
        return this;

    }


    public RaySensorBuilder setXRayMode(boolean xRayMode) {
        brick.xRayMode = xRayMode;
        return this;

    }


    public RaySensorBuilder setRange(float range) {
        brick.range = range;
        return this;

    }


    public RaySensorBuilder setAttachedRigidBody(Body rigidBody) {
        brick.attachedRigidBody = rigidBody;
        return this;

    }


    @Override
    public RaySensor getBrick() {
        RaySensor brickTemp = brick;
        brick = new RaySensor();
        return brickTemp;

    }

}
