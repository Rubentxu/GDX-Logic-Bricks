package com.indignado.logicbricks.core.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.core.controllers.Controller;

/**
 * @author Rubentxu.
 */
public class Actuator {
    public Array<Controller> controllers = new Array<Controller>();
    public String name;
    public int state;
    public Entity owner;


}
