package com.indignado.logicbricks.bricks.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public abstract class Sensor {
    // Config Values
    public String name;
    public boolean tap = false;
    public boolean initialized = false;
    public int state = -1;
    // pulse
    public boolean pulseSignal = false;


    public Sensor setName(String name) {
        this.name = name;
        return this;
    }


    public Sensor setTap(boolean tap) {
        this.tap = tap;
        return this;

    }

}
