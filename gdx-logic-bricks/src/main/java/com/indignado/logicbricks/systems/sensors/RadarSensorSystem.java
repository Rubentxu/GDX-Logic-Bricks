package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.sensors.RadarSensorComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.sensors.RadarSensor;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu
 */
public class RadarSensorSystem extends SensorSystem<RadarSensor, RadarSensorComponent> implements ContactListener, EntityListener {


    public RadarSensorSystem() {
        super(RadarSensorComponent.class);

    }


    @Override
    public boolean query(RadarSensor sensor, float deltaTime) {
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

        if (sensorA != null && sensorA instanceof RadarSensor) {
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
            processRadarSensors(entityB, contact, (RadarSensor) sensorA, true);
        } else if (sensorB != null && sensorB instanceof RadarSensor) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            processRadarSensors(entityA, contact, (RadarSensor) sensorB, true);
        }

    }


    @Override
    public void endContact(Contact contact) {
        Object sensorA = contact.getFixtureA().getUserData();
        Object sensorB = contact.getFixtureB().getUserData();

        if (sensorA != null && sensorA instanceof RadarSensor) {
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
            processRadarSensors(entityB, contact, (RadarSensor) sensorA, false);
        } else if (sensorB != null && sensorB instanceof RadarSensor) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            processRadarSensors(entityA, contact, (RadarSensor) sensorB, false);
        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    private void processRadarSensors(Entity entity, Contact contact, RadarSensor radarSensor, boolean addMode) {
        BlackBoardComponent blackBoard = entity.getComponent(BlackBoardComponent.class);
        IdentityComponent identity = entity.getComponent(IdentityComponent.class);

        if (blackBoard != null && radarSensor.propertyName != null && blackBoard.hasProperty(radarSensor.propertyName)) {
            if (addMode) radarSensor.contactList.add(contact);
            else radarSensor.contactList.remove(contact);

        } else if (radarSensor.targetTag != null && identity.tag.equals(radarSensor.targetTag)) {
            if (addMode) radarSensor.contactList.add(contact);
            else radarSensor.contactList.remove(contact);
        } else if (radarSensor.targetTag == null && radarSensor.propertyName == null){
            if (addMode) radarSensor.contactList.add(contact);
            else radarSensor.contactList.remove(contact);
        }


    }


    @Override
    public void entityAdded(Entity entity) {
        Log.debug(tag, "EntityAdded add radarSensors");
        ObjectSet<RadarSensor> radarSensors = filterRadarSensors(entity);
        if (radarSensors.size > 0) {
            RigidBodiesComponents rigidBodiesComponent = entity.getComponent(RigidBodiesComponents.class);
            if (rigidBodiesComponent == null)
                throw new LogicBricksException(tag, "Failed to create radar sensor, there is no rigidBody");
            Body body = rigidBodiesComponent.rigidBodies.first();
            Log.debug(tag, "Create Radar");
            createRadar(body, radarSensors);

        }

    }


    @Override
    public void entityRemoved(Entity entity) {

    }


    private void createRadar(Body body, ObjectSet<RadarSensor> radarSensors) {
        FixtureDefBuilder fixtureBuilder = new FixtureDefBuilder();

        Vector2[] vertices = new Vector2[8];
        vertices[0] = new Vector2();

        for (RadarSensor sensor : radarSensors) {
            if (sensor.angle > 180)
                throw new LogicBricksException(tag, "The angle of the radar can not be greater than 180");
            for (int i = 0; i < 7; i++) {
                float angle = (float) (i / 6.0 * sensor.angle) - (sensor.angle / 2) + (sensor.axis.ordinal() * 90);

                vertices[i + 1] = new Vector2(sensor.distance * MathUtils.cosDeg(angle), sensor.distance * MathUtils.sinDeg(angle));
            }
            FixtureDef radarFixture = fixtureBuilder
                    .polygonShape(vertices)
                    .sensor()
                    .build();
            body.createFixture(radarFixture).setUserData(sensor);
            Log.debug(tag, "Create Fixture sensorRadar");
        }

    }


    private ObjectSet<RadarSensor> filterRadarSensors(Entity entity) {
        ObjectSet<RadarSensor> radarSensors = new ObjectSet<RadarSensor>();
        RadarSensorComponent radarSensorComponent = entity.getComponent(RadarSensorComponent.class);
        if (radarSensorComponent != null) {
            IntMap.Values<ObjectSet<RadarSensor>> values = radarSensorComponent.sensors.values();
            while (values.hasNext()) {
                for (RadarSensor sensor : values.next()) {
                    radarSensors.add(sensor);

                }
            }
        }
        return radarSensors;

    }

}
