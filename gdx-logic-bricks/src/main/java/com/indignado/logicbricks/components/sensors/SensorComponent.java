package com.indignado.logicbricks.components.sensors;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class SensorComponent<S extends Sensor> extends Component implements Poolable {
    public IntMap<ObjectSet<S>> sensors = new IntMap<ObjectSet<S>>();


    @Override
    public void reset() {
        while (sensors.values().hasNext()) {
            for (S sensor : sensors.values().next()) {
                sensor.reset();
            }
        }
        sensors.clear();

    }

}
