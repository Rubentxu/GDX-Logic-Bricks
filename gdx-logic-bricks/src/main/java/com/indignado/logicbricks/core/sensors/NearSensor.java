package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.ObjectSet;


/**
 * @author Rubentxu
 */
public class NearSensor extends Sensor {

    // Config Values
    public String targetTag;
    public String targetPropertyName;
    public float distance = 0;
    public float resetDistance = 0;
    public Body attachedRigidBody;

    // Signal Values
    public ObjectSet<Contact> distanceContactList = new ObjectSet<Contact>();
    public ObjectSet<Contact> resetDistanceContactList = new ObjectSet<Contact>();
    public boolean initContact = false;


    @Override
    public void reset() {
        super.reset();
        targetTag = null;
        targetPropertyName = null;
        distance = 0;
        resetDistance = 0;
        attachedRigidBody = null;
        distanceContactList.clear();
        resetDistanceContactList.clear();
        initContact = false;

    }

}