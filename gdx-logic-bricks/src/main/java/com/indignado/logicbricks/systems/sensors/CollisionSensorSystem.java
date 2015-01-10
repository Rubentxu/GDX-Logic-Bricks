package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.sensors.CollisionSensorComponent;
import com.indignado.logicbricks.components.sensors.RadarSensorComponent;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.core.sensors.RadarSensor;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class CollisionSensorSystem extends SensorSystem<CollisionSensor, CollisionSensorComponent> implements ContactListener, EntityListener {
    private final ObjectSet<CollisionSensor> collisionSensors;
    private Array<ContactListener> collisionsRules;


    public CollisionSensorSystem() {
        super(CollisionSensorComponent.class);
        collisionSensors = new ObjectSet<CollisionSensor>();

    }


    public void addCollisionRule(ContactListener collisionRule) {
        if (this.collisionsRules == null) this.collisionsRules = new Array<>();
        this.collisionsRules.add(collisionRule);

    }


    @Override
    public boolean query(CollisionSensor sensor, float deltaTime) {
        for (Contact contact : sensor.contactList) {
            Log.debug(tag, "sensor contact %b", contact.isTouching());
            if(contact.isTouching()) return true;
        }
        return false;

    }


    @Override
    public void beginContact(Contact contact) {
        if (collisionsRules != null) {
            for (ContactListener rule : collisionsRules) {
                rule.beginContact(contact);
            }
        }
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        if(collisionSensors.size > 0) {
            processContactCollisionSensors(entityA, contact, true);
            processContactCollisionSensors(entityB, contact, true);

        }


    }


    @Override
    public void endContact(Contact contact) {
        if (collisionsRules != null) {
            for (ContactListener rule : collisionsRules) {
                rule.endContact(contact);
            }
        }
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        if(collisionSensors.size > 0) {
            processContactCollisionSensors(entityA, contact, false);
            processContactCollisionSensors(entityB, contact, false);

        }

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

    private void processContactCollisionSensors(Entity entity, Contact contact, boolean addMode) {
        IdentityComponent identityA = entity.getComponent(IdentityComponent.class);
        for (CollisionSensor sensor : collisionSensors) {
            if (identityA.tag.equals(sensor.targetTag)) {
                if(addMode) sensor.contactList.add(contact);
                else sensor.contactList.remove(contact);
            }
        }

    }


    @Override
    public void entityAdded(Entity entity) {
        Log.debug(tag, "EntityAdded add collisionSensors");
        sensorsHandler(entity,true);

    }


    @Override
    public void entityRemoved(Entity entity) {
        Log.debug(tag, "EntityAdded remove collisionSensors");
        sensorsHandler(entity,false);

    }


    public void sensorsHandler(Entity entity, boolean addMode) {
        CollisionSensorComponent collisionSensorComponent = entity.getComponent(CollisionSensorComponent.class);
        if (collisionSensorComponent != null) {
            IntMap.Values<ObjectSet<CollisionSensor>> values = collisionSensorComponent.sensors.values();
            while (values.hasNext()) {
                for (CollisionSensor sensor : values.next()) {
                    if (addMode) collisionSensors.add(sensor);
                    else collisionSensors.remove(sensor);
                }
            }
        }
    }

}
