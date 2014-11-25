package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class SensorBuilder<T extends Sensor> {
    private static ObjectMap<Class<? extends SensorBuilder>, SensorBuilder> buildersMap = new ObjectMap<>();
    protected T sensor;


    protected SensorBuilder() {
        createNewSensor();

    }


    public static <B extends SensorBuilder> B getInstance(Class<B> clazzBuilder) {
        B builder = (B) buildersMap.get(clazzBuilder);
        if (builder == null) {
            synchronized (clazzBuilder) {
                try {
                    builder = clazzBuilder.newInstance();
                    buildersMap.put(clazzBuilder, builder);
                } catch (Exception e) {
                    Gdx.app.log("SensorBuilder", "Error instance sensorBuilder " + clazzBuilder);
                }
            }
        }
        return builder;

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
