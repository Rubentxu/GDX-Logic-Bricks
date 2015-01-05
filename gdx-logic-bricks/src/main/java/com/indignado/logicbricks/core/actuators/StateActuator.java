package com.indignado.logicbricks.core.actuators;

/**
 * @author Rubentxu.
 */
public class StateActuator extends Actuator {
    public int state = 0;


    @Override
    public void reset() {
        super.reset();
        state = 0;

    }

}
