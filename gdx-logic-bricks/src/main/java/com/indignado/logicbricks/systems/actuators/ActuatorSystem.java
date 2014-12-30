package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.systems.LogicBrickSystem;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public abstract class ActuatorSystem<A extends Actuator, AC extends ActuatorComponent> extends LogicBrickSystem {
    protected String tag = this.getClass().getSimpleName();
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
        ObjectSet<A> actuators = (ObjectSet<A>) actuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (A actuator : actuators) {
                Log.debug(tag, "Actuator %s PulseState %s", actuator.name, actuator.pulseState);
                if (actuator.pulseState == LogicBrick.BrickMode.BM_ON) {
                    processActuator(actuator, deltaTime);
                }
                actuator.pulseState = LogicBrick.BrickMode.BM_OFF;
            }

        }

    }


    public abstract void processActuator(A actuator, float deltaTime);


}
