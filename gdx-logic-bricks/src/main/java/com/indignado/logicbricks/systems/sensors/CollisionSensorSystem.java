package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.sensors.CollisionSensor;
import com.indignado.logicbricks.components.sensors.CollisionSensorComponent;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu
 */
public class CollisionSensorSystem extends SensorSystem<CollisionSensor, CollisionSensorComponent> implements ContactListener, EntityListener {
    private final Set<CollisionSensor> collisionSensors;
    private Array<ContactListener> collisionsRules;


    public CollisionSensorSystem() {
        super(CollisionSensorComponent.class);
        collisionSensors = new HashSet<CollisionSensor>();

    }


    public void addCollisionRule(ContactListener collisionRule) {
        if(this.collisionsRules == null) this.collisionsRules = new Array<>();
        this.collisionsRules.add(collisionRule);

    }


    @Override
    public void processSensor(CollisionSensor sensor) {}


    @Override
    public void beginContact(Contact contact) {
        if(collisionsRules != null) {
            for (ContactListener rule: collisionsRules) {
                rule.beginContact(contact);
            }
        }

        processContact(contact);


    }


    @Override
    public void endContact(Contact contact) {
        if(collisionsRules != null) {
            for (ContactListener rule: collisionsRules) {
                rule.endContact(contact);
            }
        }

        processContact(contact);

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if(collisionsRules != null) {
            for (ContactListener rule: collisionsRules) {
                rule.preSolve(contact,oldManifold);
            }
        }
    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if(collisionsRules != null) {
            for (ContactListener rule: collisionsRules) {
                rule.postSolve(contact,impulse);
            }
        }
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


    @Override
    public void entityAdded(Entity entity) {
        Gdx.app.log("CollisionSensorSystem", "entityAdded");
        CollisionSensorComponent collisionSensorComponent = entity.getComponent(CollisionSensorComponent.class);
        if (collisionSensorComponent != null) {
            IntMap<Set<CollisionSensor>> map = collisionSensorComponent.sensors;
            for (int i = 0; i < map.size; ++i) {
                collisionSensors.addAll(map.get(i));
            }
        }

    }

    @Override
    public void entityRemoved(Entity entity) {
        CollisionSensorComponent collisionSensorComponent = entity.getComponent(CollisionSensorComponent.class);
        if (collisionSensorComponent != null) {
            IntMap<Set<CollisionSensor>> map = collisionSensorComponent.sensors;
            for (int i = 0; i < map.size; ++i) {
                collisionSensors.removeAll(map.get(i));
            }
        }
    }

}
