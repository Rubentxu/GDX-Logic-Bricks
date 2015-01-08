package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.physics.box2d.Contact;

/**
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    // Config Values
    public String targetTag;

    // Signal Values
    public Contact contact;


    @Override
    public void reset() {
        super.reset();
        targetTag = null;
        contact = null;

    }

}