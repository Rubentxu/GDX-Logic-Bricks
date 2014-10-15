package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class DelaySensor extends Sensor{

    private float time = 0;
    // Config Values
    public float delay = 0;

    // Signal Values
    public float deltaTimeSignal = 0;


    public DelaySensor(Entity owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;
        if( time > delay ) {
            time = 0;
            return true;
        }

        time += deltaTimeSignal;
        return false;

    }

}
