package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 * @author Rubentxu
 */
public class AlwaysSensor<T> extends Sensor{


    public AlwaysSensor(T owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;
        else return true;
    }

}
