package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;

/**
 * Created on 13/10/14.
 * @author Rubentxu
 */
public class AlwaysSensor extends Sensor{


    public AlwaysSensor(Entity owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;
        else return true;
    }

}
