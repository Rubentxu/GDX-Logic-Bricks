package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.bricks.controllers.Controller;

/**
 * @author Rubentxu.
 */
public class Actuator {
    public Array<Controller> controllers = new Array<Controller>();
    public String name;
    public int state;
    public Entity owner;


}