package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.LogicBricks;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.Sensor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created on 05/11/14.
 * @author Rubentxu
 */
public class AlwaysSensorComponent extends Component {
    public Map<String,Set<AlwaysSensor>> alwaysSensor = new HashMap<String,Set<AlwaysSensor>>();

}
