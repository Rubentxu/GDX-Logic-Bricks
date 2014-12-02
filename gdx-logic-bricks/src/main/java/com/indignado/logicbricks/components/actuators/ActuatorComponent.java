package com.indignado.logicbricks.components.actuators;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.actuators.Actuator;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public class ActuatorComponent<T extends Actuator> extends Component implements Poolable {
    public IntMap<Set<T>> actuators = new IntMap<Set<T>>();

    @Override
    public void reset() {
        actuators.clear();

    }

}
