package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.actuators.StateActuator;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.StateActuatorComponent;

import java.util.Set;

/**
 * @author Rubentxu
 */
public class StateActuatorSystem extends ActuatorSystem<StateActuator, StateActuatorComponent> {


    public StateActuatorSystem() {
        super(StateActuatorComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        StateComponent stateComponent = stateMapper.get(entity);
        Integer state = stateComponent.getCurrentState();
        Set<StateActuator> actuators = actuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (StateActuator actuator : actuators) {
                boolean evalue = evaluateController(actuator);
                if (evalue) {
                    stateComponent.changeCurrentState(actuator.state);
                }
            }
        }

    }


    @Override
    public void processActuator(StateActuator actuator) {
    }


}
