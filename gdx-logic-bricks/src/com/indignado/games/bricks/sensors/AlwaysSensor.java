package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 * @author Rubentxu
 */
public class AlwaysSensor extends Sensor{

    @Override
    public Boolean isActive() {
        if(tap && initialized) {
           return false;
        }
        initialized= true;
        return true;
    }

}
