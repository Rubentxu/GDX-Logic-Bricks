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
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        StateComponent stateComponent = stateMapper.get(entity);
        Integer state = stateComponent.getCurrentState();
        ObjectSet<StateActuator> actuators = actuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (StateActuator actuator : actuators) {
                if (actuator.pulseState == LogicBrick.BrickMode.BM_ON)
                    stateComponent.changeCurrentState(actuator.state);
            }
        }

    }


    @Override
    public void processActuator(StateActuator actuator, float deltaTime) {

    }


}
