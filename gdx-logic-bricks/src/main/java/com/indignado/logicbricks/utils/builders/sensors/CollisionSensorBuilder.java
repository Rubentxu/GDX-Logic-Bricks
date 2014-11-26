package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class CollisionSensorBuilder extends BrickBuilder<CollisionSensor> {


    public CollisionSensorBuilder setOwnerFixture(Fixture ownerFixture) {
        brick.ownerFixture = ownerFixture;
        return this;

    }

    public CollisionSensorBuilder setOwnerRigidBody(Body ownerRigidBody) {
        brick.ownerRigidBody = ownerRigidBody;
        return this;

    }

    public CollisionSensorBuilder setTargetFixture(Fixture targetFixture) {
        brick.targetFixture = targetFixture;
        return this;

    }

    public CollisionSensorBuilder setTargetRigidBody(Body targetRigidBody) {
        brick.targetRigidBody = targetRigidBody;
        return this;

    }

}
