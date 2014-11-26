package com.indignado.logicbricks.core.controllers;

import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.core.Script;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class ScriptController extends Controller {
    public Script script;
    public Array<Actuator> actuators = new Array<Actuator>();


}
