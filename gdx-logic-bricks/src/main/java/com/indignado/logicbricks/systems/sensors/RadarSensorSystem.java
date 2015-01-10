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
    private final ObjectSet<RadarSensor> radarSensors;

    public RadarSensorSystem() {
        super(RadarSensorComponent.class);
        radarSensors = new ObjectSet<RadarSensor>();

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
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        processRadarSensors(entityA, contact, true);
        processRadarSensors(entityB, contact, true);

    }


    @Override
    public void endContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        processRadarSensors(entityA, contact, false);
        processRadarSensors(entityB, contact, false);

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    private void processRadarSensors(Entity entity, Contact contact, boolean addMode) {
        BlackBoardComponent blackBoard = entity.getComponent(BlackBoardComponent.class);
        IdentityComponent identityA = entity.getComponent(IdentityComponent.class);

        for (RadarSensor radarSensor : radarSensors) {
            if (blackBoard != null && radarSensor.propertyName != null && blackBoard.hasProperty(radarSensor.propertyName)) {
                if (addMode) radarSensor.contactList.add(contact);
                else radarSensor.contactList.remove(contact);

            }
            if (radarSensor.targetTag != null && identityA.tag.equals(radarSensor.targetTag)) {
                if (addMode) radarSensor.contactList.add(contact);
                else radarSensor.contactList.remove(contact);
            }
        }


    }


    @Override
    public void entityAdded(Entity entity) {
        Log.debug(tag, "EntityAdded add radarSensors");
        sensorsHandler(entity, true);
        createRadar(entity);

    }


    @Override
    public void entityRemoved(Entity entity) {
        Log.debug(tag, "EntityRemove remove radarSensors");
        sensorsHandler(entity, false);

    }


    private void createRadar(Entity entity) {
        RigidBodiesComponents rigidBodiesComponent = entity.getComponent(RigidBodiesComponents.class);
        if (rigidBodiesComponent == null)
            throw new LogicBricksException(tag, "Failed to create radar sensor, there is no rigidBody");
        Body body = rigidBodiesComponent.rigidBodies.first();
        FixtureDefBuilder fixtureBuilder = new FixtureDefBuilder();
        for (RadarSensor sensor : radarSensors) {
            Vector2[] vertices = new Vector2[8];
            for (int i = 0; i < 7; i++) {
                float angle = (float) (i / 6.0 * sensor.angle * MathUtils.degreesToRadians);
                vertices[i + 1].set(sensor.distance * MathUtils.cos(angle), sensor.distance * MathUtils.sin(angle));
            }
            FixtureDef radarFixture = fixtureBuilder
                    .polygonShape(vertices)
                    .sensor()
                    .build();
            body.createFixture(radarFixture);

        }

    }


    private void sensorsHandler(Entity entity, boolean addMode) {
        RadarSensorComponent radarSensorComponent = entity.getComponent(RadarSensorComponent.class);
        if (radarSensorComponent != null) {
            IntMap.Values<ObjectSet<RadarSensor>> values = radarSensorComponent.sensors.values();
            while (values.hasNext()) {
                for (RadarSensor sensor : values.next()) {
                    if (sensor.targetTag != null || sensor.propertyName != null) {
                        if (addMode) radarSensors.add(sensor);
                        else radarSensors.remove(sensor);
                    }
                }
            }
        }
    }

}
