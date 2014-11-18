package com.indignado.logicbricks.bricks.sensors;

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


    public DelaySensor setDelay(float delay) {
        this.delay = delay;
        return this;

    }

}
