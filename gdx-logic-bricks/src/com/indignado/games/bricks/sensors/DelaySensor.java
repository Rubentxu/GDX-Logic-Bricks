package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class DelaySensor extends Sensor{

    private float time = 0;
    public float delay = 0;
    public float deltaTimeSignal = 0;

    @Override
    public Boolean isActive() {
        initialized= true;
        if( time > delay ) {
            time = 0;
            return true;
        }

        time += deltaTimeSignal;
        return false;

    }

}
