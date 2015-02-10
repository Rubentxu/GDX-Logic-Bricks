package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.sensors.CollisionSensorComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class CollisionSensorSystem extends SensorSystem<CollisionSensor, CollisionSensorComponent> implements ContactListener, EntityListener {
    private Array<ContactListener> collisionsRules =  new Array<>();


    public CollisionSensorSystem() {
        super(CollisionSensorComponent.class);

    }


    public void addCollisionRule(ContactListener collisionRule) {
        this.collisionsRules.add(collisionRule);

    }


    @Override
    public boolean query(CollisionSensor sensor, float deltaTime) {
        for (Contact contact : sensor.contactList) {
            Log.debug(tag, "sensor contact %b", contact.isTouching());
            if (contact.isTouching()) return true;
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

        Object sensorA = contact.getFixtureA().getUserData();
        Object sensorB = contact.getFixtureB().getUserData();

        if (sensorA != null && sensorA instanceof CollisionSensor) {
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
            processContactCollisionSensors(entityB, contact, (CollisionSensor) sensorA, true);
        } else if (sensorB != null && sensorB instanceof CollisionSensor) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            processContactCollisionSensors(entityA, contact, (CollisionSensor) sensorB, true);
        }

    }


    @Override
    public void endContact(Contact contact) {
        if (collisionsRules != null) {
            for (ContactListener rule : collisionsRules) {
                rule.endContact(contact);
            }
        }

        Object sensorA = contact.getFixtureA().getUserData();
        Object sensorB = contact.getFixtureB().getUserData();

        if (sensorA != null && sensorA instanceof CollisionSensor) {
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
            processContactCollisionSensors(entityB, contact, (CollisionSensor) sensorA, false);

        } else if (sensorB != null && sensorB instanceof CollisionSensor) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            processContactCollisionSensors(entityA, contact, (CollisionSensor) sensorB, false);

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


    private void processContactCollisionSensors(Entity entity, Contact contact, CollisionSensor collisionSensor, boolean addMode) {
        IdentityComponent identity = entity.getComponent(IdentityComponent.class);
        if (collisionSensor.targetTag != null && identity.tag.equals(collisionSensor.targetTag)) {
            if (addMode) collisionSensor.contactList.add(contact);
            else collisionSensor.contactList.remove(contact);

        }

    }


    @Override
    public void entityAdded(Entity entity) {
        Log.debug(tag, "EntityAdded add collisionSensors");
        Array<CollisionSensor> collisionSensors = filterCollisionSensors(entity);
        if (collisionSensors.size > 0) {
            RigidBodiesComponents rigidBodiesComponent = entity.getComponent(RigidBodiesComponents.class);
            if (rigidBodiesComponent == null)
                throw new LogicBricksException(tag, "Failed to create collision sensor, there is no rigidBody");
            Body body = rigidBodiesComponent.rigidBodies.first();
            if (body == null)
                throw new LogicBricksException(tag, "Failed to create collision sensor, there is no rigidBody");
            for (CollisionSensor sensor : collisionSensors) {
                for (Fixture fixture : body.getFixtureList()) {
                    fixture.setUserData(sensor);
                }

            }

        }

    }


    @Override
    public void entityRemoved(Entity entity) {

    }


    private Array<CollisionSensor> filterCollisionSensors(Entity entity) {
        Array<CollisionSensor> collisionSensors = new Array<CollisionSensor>();
        CollisionSensorComponent collisionSensorComponent = entity.getComponent(CollisionSensorComponent.class);
        if (collisionSensorComponent != null) {
            IntMap.Values<ObjectSet<CollisionSensor>> values = collisionSensorComponent.sensors.values();
            while (values.hasNext()) {
                for (CollisionSensor sensor : values.next()) {
                    if (sensor.targetTag != null) {
                        collisionSensors.add(sensor);
                    }
                }
            }
        }
        return collisionSensors;

    }

}
