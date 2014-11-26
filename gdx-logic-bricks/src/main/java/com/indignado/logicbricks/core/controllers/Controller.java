package com.indignado.logicbricks.core.controllers;

import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class Controller extends LogicBrick{
    public Array<Sensor> sensors = new Array<Sensor>();
    public boolean pulseSignal = false;

}
