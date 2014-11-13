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
    public boolean pulseSignal = false;


}
