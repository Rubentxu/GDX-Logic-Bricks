package com.indignado.logicbricks.core.sensors;

/**
 * @author Rubentxu
 */
public class DelaySensor extends Sensor {
    // Config Values
    public float delay = 0;
    public float duration = 0;
    public boolean repeat = false;

    // Signal
    public float time = 0;


    @Override
    public void reset() {
        delay = 0;
        duration = 0;
        repeat = false;
        time = 0;

    }

}
