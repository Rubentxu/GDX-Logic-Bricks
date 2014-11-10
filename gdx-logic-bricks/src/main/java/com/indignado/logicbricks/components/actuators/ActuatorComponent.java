package com.indignado.logicbricks.components.actuators;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.actuators.Actuator;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public class ActuatorComponent<T extends Actuator> extends Component {
    public IntMap<Set<T>> actuators = new IntMap<Set<T>>();

}