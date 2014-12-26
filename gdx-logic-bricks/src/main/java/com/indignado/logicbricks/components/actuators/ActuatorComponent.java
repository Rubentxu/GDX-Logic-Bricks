package com.indignado.logicbricks.components.actuators;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.actuators.Actuator;

/**
 * @author Rubentxu.
 */
public class ActuatorComponent<A extends Actuator> extends Component implements Poolable {
    public IntMap<ObjectSet<A>> actuators = new IntMap<ObjectSet<A>>();

    @Override
    public void reset() {
        actuators.clear();

    }

}
