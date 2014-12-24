package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public class LogicBrick implements Cloneable {
    public String name;
    public int state = -1;
    public Entity owner;

}
