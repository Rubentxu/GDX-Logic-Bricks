package com.indignado.logicbricks.bricks.controllers;

import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.bricks.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class Controller {
    public String name;
    public int state;
    public Array<Sensor> sensors = new Array<Sensor>();
    public boolean pulseSignal = false;

}
