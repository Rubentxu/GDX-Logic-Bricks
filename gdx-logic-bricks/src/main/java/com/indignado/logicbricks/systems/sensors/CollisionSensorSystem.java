package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.components.sensors.CollisionSensorComponent;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.sensors.CollisionSensor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu
 */
public class CollisionSensorSystem extends SensorSystem<CollisionSensor, CollisionSensorComponent> implements ContactListener, EntityListener {
    private final Set<CollisionSensor> collisionSensors;
    private LogicBricksEngine engine;
    private Array<ContactListener> collisionsRules;


    public CollisionSensorSystem() {
        super(CollisionSensorComponent.class);
        collisionSensors = new HashSet<CollisionSensor>();

    }


    public void addCollisionRule(ContactListener collisionRule) {
        if (this.collisionsRules == null) this.collisionsRules = new Array<>();
        this.collisionsRules.add(collisionRule);

    }


    @Override
    public void processSensor(CollisionSensor sensor, float deltaTime) {
    }


    @Override
    public void beginContact(Contact contact) {
        if (collisionsRules != null) {
            for (ContactListener rule : collisionsRules) {
                rule.beginContact(contact);
            }
        }

        processContact(contact);


    }


    @Override
    public void endContact(Contact contact) {
        if (collisionsRules != null) {
            for (ContactListener rule : collisionsRules) {
                rule.endContact(contact);
            }
        }

        processContact(contact);

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if (collisionsRules != null) {
            for (ContactListener rule : collisionsRules) {
                rule.preSolve(contact, oldManifold);
            }
        }
    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if (collisionsRules != null) {
            for (ContactListener rule : collisionsRules) {
                rule.postSolve(contact, impulse);
            }
        }
    }


    private void processContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        for (CollisionSensor collisionSensor : collisionSensors) {
            Array<Entity> targetEntities = engine.getEntities(collisionSensor.targetTag);

            if (targetEntities.contains(entityA, false) || targetEntities.contains(entityB, false)) {
                collisionSensor.contact = contact;
                collisionSensor.pulseSignal = contact.isTouching();
            }
        }

    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = (LogicBricksEngine) engine;

    }


    @Override
    public void entityAdded(Entity entity) {
        log.debug("EntityAdded");
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
