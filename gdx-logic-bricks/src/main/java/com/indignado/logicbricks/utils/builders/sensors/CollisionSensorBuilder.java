package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class CollisionSensorBuilder extends SensorBuilder<CollisionSensor> {

    public CollisionSensorBuilder() {
        brick = new CollisionSensor();

    }


    public CollisionSensorBuilder setTargetName(String entityName) {
        brick.targetTag = entityName;
        return this;

    }


    @Override
    public CollisionSensor getBrick() {
        CollisionSensor brickTemp = brick;
        brick = new CollisionSensor();
        return brickTemp;

    }

}
