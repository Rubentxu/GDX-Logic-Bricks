package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.sensors.NearSensorComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.sensors.NearSensor;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu
 */
public class NearSensorSystem extends SensorSystem<NearSensor, NearSensorComponent> implements ContactListener, EntityListener {


    public NearSensorSystem() {
        super(NearSensorComponent.class);

    }


    @Override
    public boolean query(NearSensor sensor, float deltaTime) {
        for (Contact contact : sensor.contactList) {
            Log.debug(tag, "sensor contact %b", contact.isTouching());
            if (contact.isTouching()) return true;
        }
        return false;

    }


    @Override
    public void beginContact(Contact contact) {
        Object sensorA = contact.getFixtureA().getUserData();
        Object sensorB = contact.getFixtureB().getUserData();

        if (sensorA != null && sensorA instanceof NearSensor) {
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
            Body bodyB = contact.getFixtureB().getBody();
            processNearSensors(entityB, bodyB, contact, (NearSensor) sensorA, true);

        } else if (sensorB != null && sensorB instanceof NearSensor) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            Body bodyA = contact.getFixtureA().getBody();
            processNearSensors(entityA, bodyA, contact, (NearSensor) sensorB, true);

        }

    }


    @Override
    public void endContact(Contact contact) {
        Object sensorA = contact.getFixtureA().getUserData();
        Object sensorB = contact.getFixtureB().getUserData();

        if (sensorA != null && sensorA instanceof NearSensor) {
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
            Body bodyB = contact.getFixtureB().getBody();
            processNearSensors(entityB, bodyB,contact, (NearSensor) sensorA, false);

        } else if (sensorB != null && sensorB instanceof NearSensor) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            Body bodyA = contact.getFixtureA().getBody();
            processNearSensors(entityA, bodyA, contact, (NearSensor) sensorB, false);

        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    private void processNearSensors(Entity entity, Body body, Contact contact, NearSensor nearSensor, boolean addMode) {
        BlackBoardComponent blackBoard = entity.getComponent(BlackBoardComponent.class);
        IdentityComponent identity = entity.getComponent(IdentityComponent.class);

        if((body.getPosition().dst(nearSensor.attachedRigidBody.getPosition()) <= nearSensor.distance && addMode) ||
                !addMode && nearSensor.contactList.contains(contact))

        if (blackBoard != null && nearSensor.targetPropertyName != null && blackBoard.hasProperty(nearSensor.targetPropertyName)) {
            if (addMode) nearSensor.contactList.add(contact);
            else nearSensor.contactList.remove(contact);

        } else if (nearSensor.targetTag != null && identity.tag.equals(nearSensor.targetTag)) {
            if (addMode) nearSensor.contactList.add(contact);
            else nearSensor.contactList.remove(contact);

        } else if (nearSensor.targetTag == null && nearSensor.targetPropertyName == null){
            if (addMode) nearSensor.contactList.add(contact);
            else nearSensor.contactList.remove(contact);
        }


    }


    @Override
    public void entityAdded(Entity entity) {
        Log.debug(tag, "EntityAdded add nearSensors");
        ObjectSet<NearSensor> nearSensors = getNearSensors(entity);
        if (nearSensors.size > 0) {
            Log.debug(tag, "Create Near");
            createNear(entity, nearSensors);

        }

    }


    @Override
    public void entityRemoved(Entity entity) {

    }


    private void createNear(Entity entity, ObjectSet<NearSensor> nearSensors) {
        FixtureDefBuilder fixtureBuilder = new FixtureDefBuilder();
        RigidBodiesComponents rigidBodiesComponent = entity.getComponent(RigidBodiesComponents.class);
        if (rigidBodiesComponent == null)
            throw new LogicBricksException(tag, "Failed to create near sensor, there is no rigidBody");

        for (NearSensor sensor : nearSensors) {
            if (sensor.distance == 0)
                throw new LogicBricksException(tag, "nearSensor distance can not be zero");
            if(sensor.attachedRigidBody == null) sensor.attachedRigidBody = rigidBodiesComponent.rigidBodies.first();

            FixtureDef nearFixture = fixtureBuilder
                    .circleShape((sensor.resetDistance == 0)? sensor.distance: sensor.resetDistance)
                    .sensor()
                    .build();
            sensor.attachedRigidBody.createFixture(nearFixture).setUserData(sensor);
            Log.debug(tag, "Create Fixture nearSensor");
        }

    }


    private ObjectSet<NearSensor> getNearSensors(Entity entity) {
        ObjectSet<NearSensor> nearSensors = new ObjectSet<NearSensor>();
        NearSensorComponent nearSensorComponent = entity.getComponent(NearSensorComponent.class);
        if (nearSensorComponent != null) {
            IntMap.Values<ObjectSet<NearSensor>> values = nearSensorComponent.sensors.values();
            while (values.hasNext()) {
                for (NearSensor sensor : values.next()) {
                    nearSensors.add(sensor);

                }
            }
        }
        return nearSensors;

    }

}
