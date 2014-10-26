package com.indignado.games.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.games.bricks.sensors.CollisionSensor;
import com.indignado.games.components.CollisionSensorComponent;

import java.util.HashSet;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensorsSystem extends EntitySystem implements ContactListener {
    private Family family;
    private ImmutableArray<Entity> entities;
    private ComponentMapper<CollisionSensorComponent> csm;
    private ObjectMap<Short, ObjectMap<Short, ContactListener>> actuators;


    public CollisionSensorsSystem() {
        super(0);
        family = Family.getFor(CollisionSensorComponent.class);
        csm = ComponentMapper.getFor(CollisionSensorComponent.class);

    }


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }


    @Override
    public void beginContact(Contact contact) {
        for (Entity entity : entities.toArray()) {
            for (CollisionSensor collisionSensor : csm.get(entity).collisionSensor) {
                processBeginContact(contact, collisionSensor, contact.getFixtureA());
                processBeginContact(contact, collisionSensor, contact.getFixtureB());

            }
        }

    }


    private void processBeginContact(Contact contact, CollisionSensor collisionSensor, Fixture fixture) {
        if (collisionSensor.targetFixtures.contains(fixture,false)) {
            if (collisionSensor.fixtureContacts.containsKey(fixture)) {
                collisionSensor.fixtureContacts.get(fixture).add(contact);
            } else {
                HashSet<Contact> constactsSet = new HashSet<Contact>();
                constactsSet.add(contact);
                collisionSensor.fixtureContacts.put(fixture, constactsSet);
            }

        }


    }


    @Override
    public void endContact(Contact contact) {
        for (Entity entity : entities.toArray()) {
            for (CollisionSensor collisionSensor : csm.get(entity).collisionSensor) {
                processEndContact(contact, collisionSensor, contact.getFixtureA());
                processEndContact(contact, collisionSensor, contact.getFixtureB());

            }
        }

    }


    private void processEndContact(Contact contact, CollisionSensor collisionSensor, Fixture fixture) {
        if (collisionSensor.targetFixtures.contains(fixture,false)) {
            if (collisionSensor.fixtureContacts.containsKey(fixture)) {
                collisionSensor.fixtureContacts.get(fixture).remove(contact);
            }
        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
