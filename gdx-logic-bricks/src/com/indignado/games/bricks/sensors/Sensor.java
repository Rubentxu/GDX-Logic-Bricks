package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 * @author Rubentxu
 */
public abstract class Sensor {
    enum Type {Always, Delay}

    public Integer freq;
    public Boolean tap;
    public Boolean initialized = false;
    public String name;
    public Integer level;
    public Type type;

    public abstract Boolean isActive();

}
