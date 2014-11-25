package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.actuators.PropertyActuator;

/**
 * @author Rubentxu.
 */
public class PropertyActuatorBuilder extends ActuatorBuilder<PropertyActuator> {


    public PropertyActuatorBuilder setTarget(Entity target) {
        actuator.target = target;
        return this;

    }


    public PropertyActuatorBuilder setProperty(String property) {
        actuator.property = property;
        return this;

    }


    public PropertyActuatorBuilder setValue(Object value) {
        actuator.value = value;
        return this;

    }


    public PropertyActuatorBuilder setMode(PropertyActuator.Mode mode) {
        actuator.mode = mode;
        return this;

    }

}
