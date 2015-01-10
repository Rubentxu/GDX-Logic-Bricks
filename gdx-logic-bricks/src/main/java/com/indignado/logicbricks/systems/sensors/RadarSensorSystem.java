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
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.sensors.RadarSensorComponent;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.sensors.RadarSensor;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class RadarSensorSystem extends SensorSystem<RadarSensor, RadarSensorComponent> implements ContactListener, EntityListener {
    private final ObjectSet<RadarSensor> radarSensorsTargetProperty;
    private final ObjectSet<RadarSensor> radarSensorsTargetTag;


    public RadarSensorSystem() {
        super(RadarSensorComponent.class);
        radarSensorsTargetProperty = new ObjectSet<RadarSensor>();
        radarSensorsTargetTag = new ObjectSet<RadarSensor>();

    }



    @Override
    public boolean query(RadarSensor sensor, float deltaTime) {
        for (Contact contact : sensor.contactList) {
            Log.debug(tag, "sensor contact %b", contact.isTouching());
            if(contact.isTouching()) return true;
        }
        return false;

    }


    @Override
    public void beginContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        if(radarSensorsTargetProperty.size > 0) {
           processRadarSensorsTargetProperty(entityA,contact,true);
           processRadarSensorsTargetProperty(entityB,contact,true);

        }

        if(radarSensorsTargetTag.size > 0) {
            processRadarSensorsTargetTag(entityA,contact,true);
            processRadarSensorsTargetTag(entityB,contact,true);

        }

    }


    @Override
    public void endContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        if(radarSensorsTargetProperty.size > 0) {
            processRadarSensorsTargetProperty(entityA,contact,false);
            processRadarSensorsTargetProperty(entityB,contact,false);

        }

        if(radarSensorsTargetTag.size > 0) {
            processRadarSensorsTargetTag(entityA,contact,false);
            processRadarSensorsTargetTag(entityB,contact,false);

        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    private void processRadarSensorsTargetProperty(Entity entity,Contact contact, boolean addMode) {
        BlackBoardComponent blackBoard = entity.getComponent(BlackBoardComponent.class);
        if(blackBoard != null) {
            for (RadarSensor radarSensor : radarSensorsTargetProperty) {
                if(blackBoard.hasProperty(radarSensor.propertyName)) {
                    if(addMode) radarSensor.contactList.add(contact);
                    else radarSensor.contactList.remove(contact);

                }

            }
        }

    }


    private void processRadarSensorsTargetTag(Entity entity, Contact contact, boolean addMode) {
        IdentityComponent identityA = entity.getComponent(IdentityComponent.class);
        for (RadarSensor radarSensor : radarSensorsTargetTag) {
            if (identityA.tag.equals(radarSensor.targetTag)) {
                if(addMode) radarSensor.contactList.add(contact);
                else radarSensor.contactList.remove(contact);
            }
        }

    }


    @Override
    public void entityAdded(Entity entity) {
        Log.debug(tag, "EntityAdded add radarSensors");
        sensorsHandler(entity,true);

    }


    @Override
    public void entityRemoved(Entity entity) {
        Log.debug(tag, "EntityRemove remove radarSensors");
        sensorsHandler(entity,false);

    }


    public void sensorsHandler(Entity entity, boolean addMode) {
        RadarSensorComponent radarSensorComponent = entity.getComponent(RadarSensorComponent.class);
        if (radarSensorComponent != null) {
            IntMap.Values<ObjectSet<RadarSensor>> values = radarSensorComponent.sensors.values();
            while (values.hasNext()) {
                for (RadarSensor sensor : values.next()) {
                    if(sensor.targetTag != null) {
                        if(addMode) radarSensorsTargetTag.add(sensor);
                        else radarSensorsTargetTag.remove(sensor);
                    }
                    if(sensor.propertyName != null) {
                        if(addMode) radarSensorsTargetProperty.add(sensor);
                        else radarSensorsTargetProperty.remove(sensor);
                    }
                }
            }
        }
    }

}
