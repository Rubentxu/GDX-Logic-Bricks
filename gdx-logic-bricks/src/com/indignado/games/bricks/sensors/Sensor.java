package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;

/**
 * Created on 13/10/14.
 * @author Rubentxu
 */
public abstract class Sensor {

    public Integer freq;
    public Boolean tap = false;
    public Boolean initialized = false;
    public String name;
    public Integer level;
    protected Entity owner;

    protected Sensor(Entity owner) {
        this.owner = owner;
    }

    public abstract Boolean isActive();


    public boolean isTap(){
        if(tap && initialized) {
            return true;
        }
        initialized= true;
        return false;
    }

}
