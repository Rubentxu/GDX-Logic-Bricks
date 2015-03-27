package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.sensors.NearSensorComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.data.Property;
import com.indignado.logicbricks.core.data.RigidBody2D;
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
        boolean isActive = false;

        if (sensor.distanceContactList.size > 0) {
            isActive = true;
            if (!sensor.initContact) sensor.initContact = true;

        } else if (sensor.initContact && sensor.resetDistanceContactList.size > 0) {
            isActive = true;

        } else if (sensor.initContact) {
            sensor.initContact = false;

        }
        return isActive;

    }


    @Override
    public void beginContact(Contact contact) {
        Object propertyA = contact.getFixtureA().getUserData();
        Object propertyB = contact.getFixtureB().getUserData();
        Log.debug(tag, "Begin contact %b A %s B %s Apos %s Bpos %s", contact.isTouching(), propertyA, propertyB,
                contact.getFixtureA().getBody().getPosition(), contact.getFixtureB().getBody().getPosition());

        if (propertyA != null && propertyA instanceof Property) {
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
            processAddNearSensors(entityB, contact, (Property) propertyA);

        } else if (propertyB != null && propertyB instanceof Property) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            processAddNearSensors(entityA, contact, (Property) propertyB);

        }

    }


    @Override
    public void endContact(Contact contact) {
        Log.debug(tag, "End contact sensor contact %b", contact.isTouching());
        Object propertyA = contact.getFixtureA().getUserData();
        Object propertyB = contact.getFixtureB().getUserData();
        Log.debug(tag, "End contact %b A %s B %s Apos %s Bpos %s", contact.isTouching(), propertyA, propertyB,
                contact.getFixtureA().getBody().getPosition(), contact.getFixtureB().getBody().getPosition());

        if (propertyA != null && propertyA instanceof Property) {
            processRemoveNearSensors(contact, (Property) propertyA);

        } else if (propertyB != null && propertyB instanceof Property) {
            processRemoveNearSensors(contact, (Property) propertyB);

        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    private void processAddNearSensors(Entity entity, Contact contact, Property property) {
        NearSensor nearSensor = (NearSensor) property.getValue();

        if (nearSensor.targetPropertyName != null) {
            BlackBoardComponent blackBoard = entity.getComponent(BlackBoardComponent.class);
            if (blackBoard.hasProperty(nearSensor.targetPropertyName)) {
                if (property.getName().equals("NearSensor")) {
                    nearSensor.distanceContactList.add(contact);
                } else if (property.getName().equals("ResetNearSensor")) {
                    nearSensor.resetDistanceContactList.add(contact);
                }
                Log.debug(tag, "Add Contact targetPropertyName");
            }

        } else if (nearSensor.targetTag != null) {
            IdentityComponent identity = entity.getComponent(IdentityComponent.class);
            if (identity.tag.equals(nearSensor.targetTag)) {
                if (property.getName().equals("NearSensor")) {
                    nearSensor.distanceContactList.add(contact);
                } else if (property.getName().equals("ResetNearSensor")) {
                    nearSensor.resetDistanceContactList.add(contact);
                }
                Log.debug(tag, "Add Contact targetTag");
            }


        } else if (nearSensor.targetTag == null && nearSensor.targetPropertyName == null) {
            if (property.getName().equals("NearSensor")) {
                nearSensor.distanceContactList.add(contact);
            } else if (property.getName().equals("ResetNearSensor")) {
                nearSensor.resetDistanceContactList.add(contact);
            }
            Log.debug(tag, "Add Contact");

        }
        Log.debug(tag, "distanceContactList size %d resetDistanceContactList %d", nearSensor.distanceContactList.size,
                nearSensor.resetDistanceContactList.size);


    }


    private void processRemoveNearSensors(Contact contact, Property property) {
        NearSensor nearSensor = (NearSensor) property.getValue();

        if (property.getName().equals("NearSensor") && nearSensor.distanceContactList.contains(contact)) {
            nearSensor.distanceContactList.remove(contact);

        } else if (property.getName().equals("ResetNearSensor") && nearSensor.resetDistanceContactList.contains(contact)) {
            nearSensor.resetDistanceContactList.remove(contact);

        }
        Log.debug(tag, "Remove NearSensor distanceContactList size %d resetDistanceContactList %d", nearSensor.distanceContactList.size,
                nearSensor.resetDistanceContactList.size);

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
            if (sensor.attachedRigidBody == null) sensor.attachedRigidBody = rigidBodiesComponent.rigidBodies.first();

            FixtureDef nearFixture = fixtureBuilder
                    .circleShape(sensor.distance)
                    .sensor()
                    .build();
            ((RigidBody2D) sensor.attachedRigidBody).body.createFixture(nearFixture).setUserData(new Property<NearSensor>("NearSensor", sensor));
            if (sensor.resetDistance != 0) {
                if (sensor.resetDistance <= sensor.distance)
                    throw new LogicBricksException(tag, "ResetDistance can not be less than or equal to the distance");

                FixtureDef nearResetFixture = fixtureBuilder
                        .circleShape(sensor.resetDistance)
                        .sensor()
                        .build();
                ((RigidBody2D) sensor.attachedRigidBody).body.createFixture(nearResetFixture).setUserData(new Property<NearSensor>("ResetNearSensor", sensor));
            }
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
