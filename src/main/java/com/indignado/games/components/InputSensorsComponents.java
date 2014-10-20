package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.indignado.games.bricks.sensors.Sensor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 17/10/14.
 *
 * @author Rubentxu
 */
public class InputSensorsComponents extends Component{
    public Set<Sensor> sensors= new HashSet<Sensor>();

}
