package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.actuators.PropertyActuator;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class PropertyBrickBuilder extends ActuatorBuilder<PropertyActuator> {


    public PropertyBrickBuilder setTarget(Entity target) {
        brick.target = target;
        return this;

    }


    public PropertyBrickBuilder setProperty(String property) {
        brick.property = property;
        return this;

    }


    public PropertyBrickBuilder setValue(Object value) {
        brick.value = value;
        return this;

    }


    public PropertyBrickBuilder setMode(PropertyActuator.Mode mode) {
        brick.mode = mode;
        return this;

    }

}
