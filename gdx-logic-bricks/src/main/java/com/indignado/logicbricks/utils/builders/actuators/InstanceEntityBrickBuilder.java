package com.indignado.logicbricks.utils.builders.actuators;

import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class InstanceEntityBrickBuilder extends ActuatorBuilder<InstanceEntityActuator> {


    public InstanceEntityBrickBuilder setType(Class clazzInstance) {
        brick.clazzInstance = clazzInstance;
        return this;

    }

    public InstanceEntityBrickBuilder setDuration(float duration) {
        brick.duration = duration;
        return this;

    }

    public InstanceEntityBrickBuilder setType(InstanceEntityActuator.Type type) {
        brick.type = type;
        return this;

    }


}
