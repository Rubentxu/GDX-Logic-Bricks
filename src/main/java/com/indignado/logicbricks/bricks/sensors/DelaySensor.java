package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class DelaySensor extends Sensor {

    // Config Values
    public float delay = 0;

    // Signal Values
    public float timeSignal = 0;


    public DelaySensor(Entity owner) {
        super(owner);

    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;
        if (timeSignal >= delay) {
            timeSignal = 0;
            return true;
        }
        return false;

    }

}
