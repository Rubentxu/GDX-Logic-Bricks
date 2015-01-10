package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.ObjectSet;


/**
 * @author Rubentxu
 */
public class RadarSensor extends Sensor {

    // Config Values
    public String targetTag;
    public String propertyName;
    public Axis axis;
    public float angle = 0;
    public float distance = 0;
    // Signal Values
    public ObjectSet<Contact> contactList = new ObjectSet<Contact>();

    @Override
    public void reset() {
        super.reset();
        targetTag = null;
        propertyName = null;
        axis = null;
        angle = 0;
        distance = 0;
        contactList.clear();

    }


    public enum Axis {Xpositive, Xnegative, Ypositive, Ynegative}

}