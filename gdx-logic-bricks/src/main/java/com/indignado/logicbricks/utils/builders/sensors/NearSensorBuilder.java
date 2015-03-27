package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.data.RigidBody;
import com.indignado.logicbricks.core.sensors.NearSensor;

/**
 * @author Rubentxu.
 */
public class NearSensorBuilder extends SensorBuilder<NearSensor> {

    public NearSensorBuilder() {
        brick = new NearSensor();

    }


    public NearSensorBuilder setTargetTag(String tag) {
        brick.targetTag = tag;
        return this;

    }


    public NearSensorBuilder setTargetPropertyName(String name) {
        brick.targetPropertyName = name;
        return this;

    }


    public NearSensorBuilder setDistance(float distance) {
        brick.distance = distance;
        return this;

    }


    public NearSensorBuilder setResetDistance(float resetDistance) {
        brick.resetDistance = resetDistance;
        return this;

    }


    public NearSensorBuilder setAttachedRigidBody(RigidBody rigidBody) {
        brick.attachedRigidBody = rigidBody;
        return this;

    }


    @Override
    public NearSensor getBrick() {
        NearSensor brickTemp = brick;
        brick = new NearSensor();
        return brickTemp;

    }

}
