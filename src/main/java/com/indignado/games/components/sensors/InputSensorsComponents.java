package com.indignado.games.components.sensors;

import com.badlogic.ashley.core.Component;
import com.indignado.games.bricks.sensors.Sensor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created on 17/10/14.
 *
 * @author Rubentxu
 */
public class InputSensorsComponents extends Component{
    public Map<String,Set<Sensor>> sensors= new HashMap<String,Set<Sensor>>();

}
