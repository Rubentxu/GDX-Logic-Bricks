package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.actuators.CameraActuator;
import com.indignado.logicbricks.core.actuators.PropertyActuator;

/**
 * @author Rubentxu.
 */
public class PropertyActuatorBuilder extends ActuatorBuilder<PropertyActuator> {

    public PropertyActuatorBuilder() {
        brick = new PropertyActuator();

    }

    public PropertyActuatorBuilder setTarget(Entity target) {
        brick.target = target;
        return this;

    }


    public PropertyActuatorBuilder setProperty(String property) {
        brick.property = property;
        return this;

    }


    public PropertyActuatorBuilder setValue(Object value) {
        brick.value = value;
        return this;

    }


    public PropertyActuatorBuilder setMode(PropertyActuator.Mode mode) {
        brick.mode = mode;
        return this;

    }


    @Override
    public PropertyActuator getBrick() {
        PropertyActuator brickTemp = brick;
        brick = new PropertyActuator();
        return brickTemp;

    }

}
