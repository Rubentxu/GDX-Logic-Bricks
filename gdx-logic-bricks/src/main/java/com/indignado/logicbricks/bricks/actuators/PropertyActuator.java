package com.indignado.logicbricks.bricks.actuators;

import com.indignado.logicbricks.data.Property;

import java.util.HashMap;

/**
 * @author Rubentxu.
 */
public class PropertyActuator<T> extends Actuator {
    public T target;
    public HashMap<String, Property> properties = new HashMap<String, Property>();
    public Mode mode;
    public enum Mode {Assign, Add, Toggle, Copy}

}
