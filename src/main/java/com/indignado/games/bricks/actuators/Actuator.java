package com.indignado.games.bricks.actuators;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public abstract class Actuator {
    public String name;
    public String state;
    protected Entity owner;

    public abstract void execute();

}
