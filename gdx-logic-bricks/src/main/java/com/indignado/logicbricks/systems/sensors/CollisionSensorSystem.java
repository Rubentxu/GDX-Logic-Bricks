package com.indignado.logicbricks.systems.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.indignado.logicbricks.bricks.sensors.CollisionSensor;
import com.indignado.logicbricks.components.sensors.CollisionSensorComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu
 */
public class CollisionSensorSystem extends SensorSystem<CollisionSensor, CollisionSensorComponent> implements ContactListener {
    private final Set<CollisionSensor> collisionSensors;


    public CollisionSensorSystem() {
        super(CollisionSensorComponent.class);
        collisionSensors = new HashSet<CollisionSensor>();

    }


    @Override
    public void processSensor(CollisionSensor sensor) {
        collisionSensors.add(sensor);

    }


    @Override
    public void clearSensor() {
        collisionSensors.clear();

    }


    @Override
    public void beginContact(Contact contact) {
        processContact(contact);

    }


    @Override
    public void endContact(Contact contact) {
        processContact(contact);

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }


    private void processContact(Contact contact) {
        for (CollisionSensor collisionSensor : collisionSensors) {
            if (collisionSensor.ownerFixture != null) {
                processFixtureContact(contact, collisionSensor, contact.getFixtureA(), contact.getFixtureB());
                processFixtureContact(contact, collisionSensor, contact.getFixtureB(), contact.getFixtureA());
                continue;
            }
            if (collisionSensor.ownerRigidBody != null) {
                processRigidBodyContact(contact, collisionSensor, contact.getFixtureA(), contact.getFixtureB());
                processRigidBodyContact(contact, collisionSensor, contact.getFixtureB(), contact.getFixtureA());

            }
        }

    }


    private void processRigidBodyContact(Contact contact, CollisionSensor collisionSensor,
                                         Fixture fixtureA, Fixture fixtureB) {
        if (collisionSensor.ownerRigidBody.getFixtureList().contains(fixtureA, false)) {
            if (collisionSensor.targetRigidBody != null && fixtureB.getBody().equals(collisionSensor.targetRigidBody)) {
                collisionSensor.contact = contact;
                collisionSensor.pulseSignal = contact.isTouching();
                Gdx.app.log("CollisionSensorSystem", "Sensor isTouching " + collisionSensor.pulseSignal);
                return;
            }

            if (collisionSensor.targetFixture != null && fixtureB.equals(collisionSensor.targetFixture)) {
                collisionSensor.contact = contact;
                collisionSensor.pulseSignal = contact.isTouching();
            }
        }

    }


    private void processFixtureContact(Contact contact, CollisionSensor collisionSensor, Fixture fixtureA, Fixture fixtureB) {
        if (collisionSensor.ownerFixture.equals(fixtureA)) {
            if (collisionSensor.targetRigidBody != null && fixtureB.getBody().equals(collisionSensor.targetRigidBody)) {
                collisionSensor.contact = contact;
                collisionSensor.pulseSignal = contact.isTouching();
                return;
            }
            if (collisionSensor.targetFixture != null && collisionSensor.targetFixture.equals(fixtureB)) {
                collisionSensor.contact = contact;
                collisionSensor.pulseSignal = contact.isTouching();

            }
        }

    }

}
