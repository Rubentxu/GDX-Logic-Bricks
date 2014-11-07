package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public abstract class ActuatorSystem<A extends Actuator, AC extends ActuatorComponent> extends IteratingSystem {
    protected ComponentMapper<AC> actuatorMapper;
    protected ComponentMapper<StateComponent> stateMapper;


    public ActuatorSystem(Class<AC> clazz) {
        super(Family.getFor(clazz, StateComponent.class), 2);
        this.actuatorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).get();
        Set<A> actuators = (Set<A>) actuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (A actuator : actuators) {
                processActuator(actuator);

            }
        }

    }


    public abstract void processActuator(A actuator);


    protected boolean evaluateController(Actuator actuator) {
        Iterator<Controller> it = actuator.controllers.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ActuatorSystem", "This actuator does not have any associated controller");
        while (it.hasNext()) {
            if (it.next().pulseSignal == false) return false;

        }
        return true;
    }

}
