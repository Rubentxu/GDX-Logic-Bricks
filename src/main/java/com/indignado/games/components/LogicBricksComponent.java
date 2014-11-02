package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.indignado.games.bricks.LogicBricks;
import com.indignado.games.bricks.actuators.Actuator;
import com.indignado.games.bricks.controllers.Controller;
import com.indignado.games.bricks.sensors.CollisionSensor;
import com.indignado.games.bricks.sensors.Sensor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBricksComponent extends Component {
    public Map<String,Set<LogicBricks>> logicBricks = new HashMap<String,Set<LogicBricks>>();

}
