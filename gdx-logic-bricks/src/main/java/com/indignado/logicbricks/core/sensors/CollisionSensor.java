package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.ObjectSet;

/**
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    // Config Values
    public String targetTag;

    // Signal Values
    public ObjectSet<Contact> contactList = new ObjectSet<Contact>();


    @Override
    public void reset() {
        super.reset();
        targetTag = null;
        contactList.clear();

    }

}