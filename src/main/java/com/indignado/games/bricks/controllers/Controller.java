package com.indignado.games.bricks.controllers;

import com.badlogic.gdx.utils.Array;
import com.indignado.games.bricks.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class Controller {
    public String name;
    public Array<Sensor> sensors;
    public boolean pulseSignal = false;

}
