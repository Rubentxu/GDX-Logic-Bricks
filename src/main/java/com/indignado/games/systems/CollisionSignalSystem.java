package com.indignado.games.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.*;
import com.indignado.games.bricks.LogicBricks;
import com.indignado.games.bricks.sensors.CollisionSensor;
import com.indignado.games.bricks.sensors.Sensor;
import com.indignado.games.components.LogicBricksComponent;
import com.indignado.games.components.StateComponent;

import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSignalSystem extends EntitySystem implements ContactListener {
    private Family family;
    private ImmutableArray<Entity> entities;
    private ComponentMapper<LogicBricksComponent> logicBricksMapper;
    private ComponentMapper<StateComponent> stateMapper;


    public CollisionSignalSystem() {
        super(0);
        family = Family.getFor(LogicBricksComponent.class, StateComponent.class);
        logicBricksMapper = ComponentMapper.getFor(LogicBricksComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

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
        for (int i = 0; i < entities.size(); ++i) {
            String state = stateMapper.get(entities.get(i)).get();
            Set<LogicBricks> logicBricks = logicBricksMapper.get(entities.get(i)).logicBricks.get(state);
            for (LogicBricks brick : logicBricks) {
                if (brick.sensors.containsKey(CollisionSensor.class)) {
                    Set<CollisionSensor> sensors = (Set<CollisionSensor>) brick.sensors.get(CollisionSensor.class);
                    for (CollisionSensor collisionSensor : sensors) {
                        if (collisionSensor.ownerFixture != null) {
                            processFixtureBeginContact(contact, collisionSensor, contact.getFixtureA(), contact.getFixtureB());
                            processFixtureBeginContact(contact, collisionSensor, contact.getFixtureB(), contact.getFixtureA());
                            continue;
                        }

                        if (collisionSensor.ownerRigidBody != null) {
                            processRigidBodyBeginContact(contact, collisionSensor, contact.getFixtureA(), contact.getFixtureB());
                            processRigidBodyBeginContact(contact, collisionSensor, contact.getFixtureB(), contact.getFixtureA());
                            continue;
                        }

                    }
                }
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
        for (int i = 0; i < entities.size(); ++i) {
            String state = stateMapper.get(entities.get(i)).get();
            Set<LogicBricks> logicBricks = logicBricksMapper.get(entities.get(i)).logicBricks.get(state);
            for (LogicBricks brick : logicBricks) {
                if (brick.sensors.containsKey(CollisionSensor.class)) {
                    Set<CollisionSensor> sensors = (Set<CollisionSensor>) brick.sensors.get(CollisionSensor.class);
                    for (CollisionSensor collisionSensor : sensors) {
                        processEndContact(contact, collisionSensor, contact.getFixtureA(), contact.getFixtureB());
                        processEndContact(contact, collisionSensor, contact.getFixtureB(), contact.getFixtureA());
                    }
                }
            }
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
