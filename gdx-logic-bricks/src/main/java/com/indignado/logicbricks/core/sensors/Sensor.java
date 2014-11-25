package com.indignado.logicbricks.core.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public abstract class Sensor {
    // Config Values
    public String name;
    public int state = -1;
    // pulse
    public boolean pulseSignal = false;


    public Sensor setName(String name) {
        this.name = name;
        return this;
    }

}
