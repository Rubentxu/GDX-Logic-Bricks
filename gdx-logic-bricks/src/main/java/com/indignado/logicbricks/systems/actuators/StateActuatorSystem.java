package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
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
        Gdx.app.log("StateActuatorSystem", "state " + state );
        Set<StateActuator> actuators = actuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (StateActuator actuator : actuators) {
                boolean evalue = evaluateController(actuator);
                Gdx.app.log("StateActuatorSystem", "state " + state + " evalue " + evalue);
                if (evalue) {
                    Gdx.app.log("StateActuatorSystem", "state " + state + " actuator state " + actuator.state);
                    stateComponent.changeCurrentState(actuator.state);
                }


            }
        }

    }


    @Override
    public void processActuator(StateActuator actuator) {

    }


}
