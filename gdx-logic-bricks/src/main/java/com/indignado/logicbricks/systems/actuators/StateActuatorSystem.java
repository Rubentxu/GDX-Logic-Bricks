package com.indignado.logicbricks.systems.actuators;

import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.StateActuatorComponent;
import com.indignado.logicbricks.core.actuators.StateActuator;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class StateActuatorSystem extends ActuatorSystem<StateActuator, StateActuatorComponent> {


    public StateActuatorSystem() {
        super(StateActuatorComponent.class);

    }


    @Override
    public void processActuator(StateActuator actuator, float deltaTime) {
        StateComponent stateComponent = stateMapper.get(actuator.owner);
        Log.debug(tag, "E) Actuator %s changeState %s", actuator.name, actuator.changeState);
        stateComponent.changeCurrentState(actuator.changeState);


    }


}
