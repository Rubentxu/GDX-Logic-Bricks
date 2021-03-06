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


    @Override
    public void reset() {
        super.reset();
        target = null;
        property = null;
        value = null;
        mode = null;

    }


    public enum Mode {Assign, Add, Toggle, Copy}

}
