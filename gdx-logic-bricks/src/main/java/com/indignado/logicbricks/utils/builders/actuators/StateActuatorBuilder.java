package com.indignado.logicbricks.utils.builders.actuators;

import com.indignado.logicbricks.core.actuators.StateActuator;

/**
 * @author Rubentxu.
 */
public class StateActuatorBuilder extends ActuatorBuilder<StateActuator> {

    public StateActuatorBuilder() {
        brick = new StateActuator();

    }


    public StateActuatorBuilder setState(int state) {
        brick.state = state;
        return this;

    }


    @Override
    public StateActuator getBrick() {
        StateActuator brickTemp = brick;
        brick = new StateActuator();
        return brickTemp;

    }

}
