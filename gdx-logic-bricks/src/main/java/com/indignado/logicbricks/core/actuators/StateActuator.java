package com.indignado.logicbricks.core.actuators;

/**
 * @author Rubentxu.
 */
public class StateActuator extends Actuator {
    public int changeState = 0;


    @Override
    public void reset() {
        super.reset();
        changeState = 0;

    }

}
