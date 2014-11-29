package com.indignado.logicbricks.utils.builders.actuators;

import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public abstract class ActuatorBuilder<T extends Actuator> extends BrickBuilder<T> {

    public ActuatorBuilder addController(Controller controller) {
        brick.controllers.add(controller);
        return this;

    }


}
