package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.*;
import com.indignado.logicbricks.bricks.sensors.CollisionSensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.CollisionSensorComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensorSystem extends IteratingSystem implements ContactListener {
    private final Set<CollisionSensor> collisionSensors;
    private ComponentMapper<CollisionSensorComponent> collisionSensorMapper;
    private ComponentMapper<StateComponent> stateMapper;


    public CollisionSensorSystem() {
        super(Family.getFor(CollisionSensorComponent.class, StateComponent.class));
        collisionSensorMapper = ComponentMapper.getFor(CollisionSensorComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        collisionSensors = new HashSet<CollisionSensor>();

    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).get();
        Set<CollisionSensor> sensors = collisionSensorMapper.get(entity).sensors.get(state);
        if (sensors != null) {
            for (CollisionSensor sensor : sensors) {
                if (!sensor.isTap() && !collisionSensors.contains(sensor)) {
                    collisionSensors.add(sensor);
                }
            }
        }

    }


    @Override
    public void beginContact(Contact contact) {
        for (CollisionSensor collisionSensor : collisionSensors) {
            if (collisionSensor.ownerFixture != null) {
                processFixtureBeginContact(contact, collisionSensor, contact.getFixtureA(), contact.getFixtureB());
                processFixtureBeginContact(contact, collisionSensor, contact.getFixtureB(), contact.getFixtureA());
                continue;
            }

            if (collisionSensor.ownerRigidBody != null) {
                processRigidBodyBeginContact(contact, collisionSensor, contact.getFixtureA(), contact.getFixtureB());
                processRigidBodyBeginContact(contact, collisionSensor, contact.getFixtureB(), contact.getFixtureA());

            }

        }


    }

    private void processRigidBodyBeginContact(Contact contact, CollisionSensor collisionSensor,
                                              Fixture fixtureA, Fixture fixtureB) {
        if (collisionSensor.ownerRigidBody.getFixtureList().contains(fixtureA, false)) {
            if (collisionSensor.targetBody != null && fixtureB.getBody().equals(collisionSensor.targetBody)) {
                collisionSensor.contact = contact;
                return;
            }

            if (collisionSensor.targetFixture != null && fixtureB.equals(collisionSensor.targetFixture)) {
                collisionSensor.contact = contact;

            }

        }
    }


    private void processFixtureBeginContact(Contact contact, CollisionSensor collisionSensor, Fixture fixtureA, Fixture fixtureB) {
        if (collisionSensor.ownerFixture.equals(fixtureA)) {
            if (collisionSensor.targetBody != null && fixtureB.getBody().equals(collisionSensor.targetBody)) {
                collisionSensor.contact = contact;
                return;
            }

            if (collisionSensor.targetFixture != null && collisionSensor.targetFixture.equals(fixtureB)) {
                collisionSensor.contact = contact;

            }

        }

    }


    @Override
    public void endContact(Contact contact) {
        for (CollisionSensor collisionSensor : collisionSensors) {
            processEndContact(contact, collisionSensor, contact.getFixtureA(), contact.getFixtureB());
            processEndContact(contact, collisionSensor, contact.getFixtureB(), contact.getFixtureA());
        }

    }


    private void processEndContact(Contact contact, CollisionSensor collisionSensor, Fixture fixtureA, Fixture fixtureB) {
        if (collisionSensor.ownerFixture.equals(fixtureA)) {
            if (collisionSensor.targetBody != null && fixtureB.getBody().equals(collisionSensor.targetBody)) {
                collisionSensor.contact = contact;
            }

            if (collisionSensor.targetFixture != null && collisionSensor.targetFixture.equals(fixtureB)) {
                collisionSensor.contact = contact;
            }

        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        System.out.println("Presolve");
    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        System.out.println("Postsolve");
    }


}
