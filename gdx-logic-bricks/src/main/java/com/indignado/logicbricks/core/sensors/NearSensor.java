package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Transform;
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
    public ObjectSet<Contact> contactList = new ObjectSet<Contact>();


    @Override
    public void reset() {
        super.reset();
        targetTag = null;
        targetPropertyName = null;
        distance = 0;
        resetDistance = 0;
        attachedRigidBody = null;
        contactList.clear();

    }

}