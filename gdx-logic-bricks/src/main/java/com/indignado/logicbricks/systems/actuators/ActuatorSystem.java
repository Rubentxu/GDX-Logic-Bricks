package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.actuators.StateActuator;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.data.LogicBrick;
import com.indignado.logicbricks.systems.LogicBrickSystem;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public abstract class ActuatorSystem<A extends Actuator, AC extends ActuatorComponent> extends LogicBrickSystem {
    protected ComponentMapper<AC> actuatorMapper;
    protected ComponentMapper<StateComponent> stateMapper;


    public ActuatorSystem(Class<AC> clazz) {
        super(Family.all(clazz, StateComponent.class).get(), 3);
        this.actuatorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    public ActuatorSystem(Class<AC> clazz, Class<? extends Component> clazz2) {
        super(Family.all(clazz, clazz2, StateComponent.class).get(), 3);
        this.actuatorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        Integer state = stateMapper.get(entity).getCurrentState();
        Log.debug(tag, "A) current state %d name %s time " + stateMapper.get(entity).time + " ", state, stateMapper.get(entity).getCurrentStateName());
        ObjectSet<A> actuators = (ObjectSet<A>) actuatorMapper.get(entity).actuators.get(state);

        if (actuators != null) {
            for (A actuator : actuators) {
                Log.debug(tag, "B) Actuator %s size controller %d", actuator.name, actuator.controllers.size);
                actuator.pulseState = LogicBrick.BrickMode.BM_IDLE;
                for (Controller controller : actuator.controllers) {
                    if (controller.pulseState.equals(LogicBrick.BrickMode.BM_ON)) {
                        Log.debug(tag, "C) Controller %s pulseState %s", controller.name, controller.pulseState);
                        actuator.pulseState = LogicBrick.BrickMode.BM_ON;
                    } else {
                        actuator.pulseState = LogicBrick.BrickMode.BM_OFF;
                        break;
                    }
                }
                if (actuator.pulseState.equals(LogicBrick.BrickMode.BM_ON)) {
                    Log.debug(tag, "D) Actuator %s pulseState %s", actuator.name, actuator.pulseState);
                    processActuator(actuator, deltaTime);
                    if (actuator instanceof StateActuator) break;

                }
            }
        }

    }


    public abstract void processActuator(A actuator, float deltaTime);


}
