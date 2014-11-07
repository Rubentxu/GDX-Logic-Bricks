package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;

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
    public int state;
    public boolean pulseSignal = false;



}
