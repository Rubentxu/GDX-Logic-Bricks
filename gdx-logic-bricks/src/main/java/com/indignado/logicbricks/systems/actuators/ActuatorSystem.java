package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.utils.Log;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public abstract class ActuatorSystem<A extends Actuator, AC extends ActuatorComponent> extends IteratingSystem {
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
        Set<A> actuators = (Set<A>) actuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (A actuator : actuators) {
                processActuator(actuator, deltaTime);

            }
        }

    }


    public abstract void processActuator(A actuator, float deltaTime);


    protected boolean evaluateController(Actuator actuator) {
        Iterator<Controller> controllers = actuator.controllers.iterator();
        if (!controllers.hasNext())
            throw new LogicBricksException("ActuatorSystem", "This sensor does not have any associated sensor");
        while (controllers.hasNext()) {
            Controller controller = controllers.next();
            boolean signal = controller.pulseSignal;
            if (signal == false) return false;
        }
        return true;

    }

}
