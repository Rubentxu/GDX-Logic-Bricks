package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class DelaySensor<T> extends Sensor{

    private float time = 0;
    // Config Values
    public float delay = 0;

    // Signal Values
    public float deltaTimeSignal = 0;


    public DelaySensor(T owner) {
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
