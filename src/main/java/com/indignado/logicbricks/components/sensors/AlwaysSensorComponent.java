package com.indignado.logicbricks.components.sensors;

import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;

import java.util.Set;

/**
 * Created on 05/11/14.
 *
 * @author Rubentxu
 */
public class AlwaysSensorComponent extends SensorComponent {
    public IntMap<Set<AlwaysSensor>> alwaysSensors = new IntMap<Set<AlwaysSensor>>();

}
