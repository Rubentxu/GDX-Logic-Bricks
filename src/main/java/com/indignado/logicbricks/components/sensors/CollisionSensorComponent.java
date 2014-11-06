package com.indignado.logicbricks.components.sensors;

import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.sensors.CollisionSensor;

import java.util.Set;

/**
 * Created on 05/11/14.
 *
 * @author Rubentxu
 */
public class CollisionSensorComponent extends SensorComponent {
    public IntMap<Set<CollisionSensor>> collisionSensors = new IntMap<Set<CollisionSensor>>();

}
