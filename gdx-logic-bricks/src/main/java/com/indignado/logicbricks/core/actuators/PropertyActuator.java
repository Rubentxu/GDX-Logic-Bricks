package com.indignado.logicbricks.core.actuators;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public class PropertyActuator<T> extends Actuator {
    public Entity target;
    public String property;
    public T value;

    public Mode mode;

    public PropertyActuator setTarget(Entity target) {
        this.target = target;
        return this;

    }

    public PropertyActuator setProperty(String property) {
        this.property = property;
        return this;

    }

    public PropertyActuator setValue(T value) {
        this.value = value;
        return this;

    }

    public PropertyActuator setMode(Mode mode) {
        this.mode = mode;
        return this;

    }


    public enum Mode {Assign, Add, Toggle, Copy}

}
