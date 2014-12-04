package com.indignado.logicbricks.utils.builders.actuators;

import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuatorBuilder extends ActuatorBuilder<InstanceEntityActuator> {

    public InstanceEntityActuatorBuilder() {
        brick = new InstanceEntityActuator();

    }

    public InstanceEntityActuatorBuilder setEntityFactory(EntityFactory entityFactory) {
        brick.entityFactory = entityFactory;
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


    @Override
    public InstanceEntityActuator getBrick() {
        InstanceEntityActuator brickTemp = brick;
        brick = new InstanceEntityActuator();
        return brickTemp;

    }

}
