package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.core.sensors.*;

/**
 * @author Rubentxu.
 */
public class SensorBuilder<T extends Sensor> {
    private static ObjectMap<Class<? extends Sensor>, SensorBuilder> buildersMap = new ObjectMap<>();
    protected T sensor;


    protected SensorBuilder() {
        buildersMap.put(CollisionSensor.class, new CollisionSensorBuilder());
        buildersMap.put(KeyboardSensor.class, new KeyboardSensorBuilder());
        buildersMap.put(MessageSensor.class, new MessageSensorBuilder());
        buildersMap.put(MouseSensor.class, new MouseSensorBuilder());
        buildersMap.put(PropertySensor.class, new PropertySensorBuilder());
        createNewSensor();

    }


    public static <B extends SensorBuilder> B getInstance(Class<? extends Sensor> clazzSensor) {
        return (B) buildersMap.get(clazzSensor);


    }


    private void createNewSensor() {
        try {
            this.sensor = (T) sensor.getClass().newInstance();

        } catch (Exception e) {
            Gdx.app.log("SensorBuilder", "Error instance sensor " + sensor.getClass());
        }

    }


    public SensorBuilder<T> setName(String name) {
        sensor.name = name;
        return this;

    }


    public SensorBuilder<T> setState(int state) {
        sensor.state = state;
        return this;

    }


    public T getSensor() {
        T sensorTemp = sensor;
        createNewSensor();
        return sensorTemp;

    }

}
