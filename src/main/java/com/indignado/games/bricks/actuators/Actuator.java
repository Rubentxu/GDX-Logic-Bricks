package com.indignado.games.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.indignado.games.bricks.controllers.Controller;
import com.indignado.games.bricks.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class Actuator {
    public Array<Controller> controllers;
    public String name;
    public Entity owner;


}
