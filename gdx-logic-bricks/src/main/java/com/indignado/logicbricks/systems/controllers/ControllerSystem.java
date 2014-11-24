package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.core.controllers.Controller;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public abstract class ControllerSystem<C extends Controller, CC extends ControllerComponent> extends IteratingSystem {
    protected ComponentMapper<CC> controllerMapper;
    protected ComponentMapper<StateComponent> stateMapper;

    public ControllerSystem(Class<CC> clazz) {
        super(Family.all(clazz, StateComponent.class).get(), 2);
        this.controllerMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).getCurrentState();
        Set<C> controllers = (Set<C>) controllerMapper.get(entity).controllers.get(state);
        if (controllers != null) {
            for (C controller : controllers) {
                processController(controller);

            }
        }

    }


    public abstract void processController(C controller);

}