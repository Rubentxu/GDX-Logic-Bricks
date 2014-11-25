package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.indignado.logicbricks.core.sensors.CollisionSensor;

/**
 * @author Rubentxu.
 */
public class CollisionSensorBuilder extends SensorBuilder<CollisionSensor> {


    public CollisionSensorBuilder setOwnerFixture(Fixture ownerFixture) {
        sensor.ownerFixture = ownerFixture;
        return this;

    }

    public CollisionSensorBuilder setOwnerRigidBody(Body ownerRigidBody) {
        sensor.ownerRigidBody = ownerRigidBody;
        return this;

    }

    public CollisionSensorBuilder setTargetFixture(Fixture targetFixture) {
        sensor.targetFixture = targetFixture;
        return this;

    }

    public CollisionSensorBuilder setTargetRigidBody(Body targetRigidBody) {
        sensor.targetRigidBody = targetRigidBody;
        return this;

    }

}
