package com.indignado.logicbricks.components.sensors;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.sensors.Sensor;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public class SensorComponent<S extends Sensor> extends Component implements Poolable {
    public IntMap<Set<S>> sensors = new IntMap<Set<S>>();


    @Override
    public void reset() {
        sensors.clear();

    }

}
