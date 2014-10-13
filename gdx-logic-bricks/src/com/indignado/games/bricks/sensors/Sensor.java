package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 * @author Rubentxu
 */
public abstract class Sensor {
    enum Type {Always, Delay}

    public Integer freq;
    public Boolean tap = false;
    public Boolean initialized = false;
    public String name;
    public Integer level;
    public Type type;

    public abstract Boolean isActive();


    public boolean isTap(){
        if(tap && initialized) {
            return true;
        }
        initialized= true;
        return false;
    }

}
