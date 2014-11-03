package com.indignado.games.bricks;

import com.indignado.games.bricks.actuators.Actuator;
import com.indignado.games.bricks.controllers.Controller;
import com.indignado.games.bricks.sensors.Sensor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBricks {
    public String name;
    public Map<Class<? extends Sensor>,Set<? extends Sensor>> sensors = new HashMap<Class<? extends Sensor>,Set<? extends Sensor>>();
    public Map<Class<? extends Controller>,Set<? extends Controller>> controllers = new HashMap<Class<? extends Controller>,Set<? extends Controller>>();
    public Map<Class<? extends Actuator>,Set<? extends Actuator>> actuators = new HashMap<Class<? extends Actuator>,Set<? extends Actuator>>();

}
