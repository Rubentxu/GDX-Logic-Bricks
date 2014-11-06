package com.indignado.logicbricks.bricks;

import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.sensors.Sensor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBricks {
    public String name;
    public Map<Class<? extends Sensor>, Set<Sensor>> sensors = new HashMap<Class<? extends Sensor>, Set<Sensor>>();
    public Map<Class<? extends Controller>, Set<Controller>> controllers = new HashMap<Class<? extends Controller>, Set<Controller>>();
    public Map<Class<? extends Actuator>, Set<Actuator>> actuators = new HashMap<Class<? extends Actuator>, Set<Actuator>>();

}
