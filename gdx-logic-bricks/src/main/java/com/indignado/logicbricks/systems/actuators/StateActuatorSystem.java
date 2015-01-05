package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.StateActuatorComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.Settings;
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
        stateComponent.changeCurrentState(actuator.state);

    }


}
