package com.indignado.logicbricks.components.sensors;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class SensorComponent<S extends Sensor> implements Poolable, Component {
    public IntMap<ObjectSet<S>> sensors = new IntMap<ObjectSet<S>>();


    @Override
    public void reset() {
        sensors.clear();

    }

}
