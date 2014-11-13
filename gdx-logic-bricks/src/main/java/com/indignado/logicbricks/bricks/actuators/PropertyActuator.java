package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public class PropertyActuator<T> extends Actuator {
    public Entity target;
    public String property;
    public T value;

    public Mode mode;

    public enum Mode {Assign, Add, Toggle, Copy}

}
