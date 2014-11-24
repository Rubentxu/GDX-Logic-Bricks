package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    // Config Values
    public Fixture ownerFixture;
    public Body ownerRigidBody;
    public Fixture targetFixture;
    public Body targetRigidBody;


    // Signal Values
    public Contact contact;


    public CollisionSensor setOwnerFixture(Fixture ownerFixture) {
        this.ownerFixture = ownerFixture;
        return this;

    }

    public CollisionSensor setOwnerRigidBody(Body ownerRigidBody) {
        this.ownerRigidBody = ownerRigidBody;
        return this;

    }

    public CollisionSensor setTargetFixture(Fixture targetFixture) {
        this.targetFixture = targetFixture;
        return this;

    }

    public CollisionSensor setTargetRigidBody(Body targetRigidBody) {
        this.targetRigidBody = targetRigidBody;
        return this;

    }

}