package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.core.actuators.Actuator;

/**
 * @author Rubentxu.
 */
public class ActuatorBuilder<T extends Actuator> {
    private static ObjectMap<Class<? extends ActuatorBuilder>, ActuatorBuilder> buildersMap = new ObjectMap<>();
    protected T actuator;


    protected ActuatorBuilder() {
        createNewActuator();
    }


    public static <B extends ActuatorBuilder> B getInstance(Class<B> clazzBuilder) {
        B builder = (B) buildersMap.get(clazzBuilder);
        if (builder == null) {
            synchronized (clazzBuilder) {
                try {
                    builder = clazzBuilder.newInstance();
                    buildersMap.put(clazzBuilder, builder);
                } catch (Exception e) {
                    Gdx.app.log("ActuatorBuilder", "Error instance actuatorBuilder " + clazzBuilder);
                }
            }
        }

        return builder;

    }


    private void createNewActuator() {
        try {
            this.actuator = (T) actuator.getClass().newInstance();

        } catch (Exception e) {
            Gdx.app.log("ActuatorBuilder", "Error instance sensor " + actuator.getClass());
        }

    }


    public ActuatorBuilder<T> setName(String name) {
        actuator.name = name;
        return this;

    }


    public ActuatorBuilder<T> setOwner(Entity owner) {
        actuator.owner = owner;
        return this;

    }


    public T getActuator() {
        T actuatorTemp = actuator;
        createNewActuator();
        return actuatorTemp;

    }

}
