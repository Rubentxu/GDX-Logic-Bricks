package com.indignado.logicbricks.utils.builders.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class ControllerBuilder<T extends Controller> {
    private static ObjectMap<Class<? extends ControllerBuilder>, ControllerBuilder> buildersMap = new ObjectMap<>();
    protected T controller;


    protected ControllerBuilder() {
        createNewController();

    }


    public static <B extends ControllerBuilder> B getInstance(Class<B> clazzBuilder) {
        B builder = (B) buildersMap.get(clazzBuilder);
        if (builder == null) {
            synchronized (clazzBuilder) {
                try {
                    builder = clazzBuilder.newInstance();
                    buildersMap.put(clazzBuilder, builder);
                } catch (Exception e) {
                    Gdx.app.log("ControllerBuilder", "Error instance controllerBuilder " + clazzBuilder);
                }
            }
        }
        return builder;

    }


    private void createNewController() {
        try {
            this.controller = (T) controller.getClass().newInstance();

        } catch (Exception e) {
            Gdx.app.log("ControllerBuilder", "Error instance sensor " + controller.getClass());
        }

    }


    public ControllerBuilder<T> setName(String name) {
        controller.name = name;
        return this;

    }


    public ControllerBuilder<T> setState(int state) {
        controller.state = state;
        return this;

    }


    public ControllerBuilder<T> addSensor(Sensor sensor) {
        controller.sensors.add(sensor);
        return this;

    }


    public T getController() {
        T controllerTemp = controller;
        createNewController();
        return controllerTemp;

    }

}
