package com.indignado.logicbricks.utils.builders.actuators;

import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuatorBuilder extends ActuatorBuilder<InstanceEntityActuator> {


    public InstanceEntityActuatorBuilder setType(Class clazzInstance) {
        brick.clazzInstance = clazzInstance;
        return this;

    }

    public InstanceEntityActuatorBuilder setDuration(float duration) {
        brick.duration = duration;
        return this;

    }

    public InstanceEntityActuatorBuilder setType(InstanceEntityActuator.Type type) {
        brick.type = type;
        return this;

    }


}
