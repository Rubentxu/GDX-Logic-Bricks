package com.indignado.games.bricks.controllers;

import com.badlogic.gdx.utils.Array;
import com.indignado.games.bricks.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public abstract class Controller {
    public String name;
    public String state;

    public abstract Boolean evaluate(Array<Sensor> sensors);

}
