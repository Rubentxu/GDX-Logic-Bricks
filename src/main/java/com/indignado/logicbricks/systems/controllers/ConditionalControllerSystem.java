package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.controllers.Script;
import com.indignado.logicbricks.bricks.controllers.ScriptController;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;

import java.util.Iterator;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class ConditionalControllerSystem extends IteratingSystem {
    private ComponentMapper<StateComponent> stateMapper;
    private ComponentMapper<ConditionalControllerComponent> conditionalControllerMapper;


    public ConditionalControllerSystem() {
        super(Family.getFor(ConditionalControllerComponent.class, StateComponent.class));
        conditionalControllerMapper = ComponentMapper.getFor(ConditionalControllerComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) throws LogicBricksException {
        Integer state = stateMapper.get(entity).get();
        Set<ConditionalController> conditionalControllers = conditionalControllerMapper.get(entity).controllers.get(state);
        if (conditionalControllers != null) {
            for (ConditionalController controller : conditionalControllers) {
                if (controller.type.equals(ConditionalController.Type.AND)) evaluateAndConditional(controller);
                if (controller.type.equals(ConditionalController.Type.OR)) evaluateOrConditional(controller);
            }

        }

    }


    public void evaluateAndConditional(ConditionalController controller) {
        Iterator<Sensor> it = controller.sensors.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()) {
            Sensor s = it.next();
            if (s.isActive().equals(false)) {
                controller.pulseSignal = false;
                return;

            }

        }
        controller.pulseSignal = true;
    }


    public void evaluateOrConditional(ConditionalController controller) {
        Iterator<Sensor> it = controller.sensors.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()) {
            Sensor s = it.next();
            if (s.isActive().equals(true)) {
                controller.pulseSignal = true;
                return;
            }
        }
        controller.pulseSignal = false;

    }


    public void evaluate(ScriptController controller) {
        Iterator<Script> it = controller.scripts.iterator();
        while (it.hasNext()) {
            it.next().execute(controller.sensors);

        }

    }

}
