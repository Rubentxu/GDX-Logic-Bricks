package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.LogicBrickSystem;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public abstract class ControllerSystem<C extends Controller, CC extends ControllerComponent> extends LogicBrickSystem {
    protected String tag = this.getClass().getSimpleName();
    protected ComponentMapper<CC> controllerMapper;
    protected ComponentMapper<StateComponent> stateMapper;

    public ControllerSystem(Class<CC> clazz) {
        super(Family.all(clazz, StateComponent.class).get(), 2);
        this.controllerMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        Integer state = stateMapper.get(entity).getCurrentState();
        ObjectSet<C> controllers = (ObjectSet<C>) controllerMapper.get(entity).controllers.get(state);
        if (controllers != null) {
            for (C controller : controllers) {
                controller.pulseState = BrickMode.BM_IDLE;
                for(Sensor sens : controller.sensors.values()) {
                    if(sens.pulseState.equals(BrickMode.BM_OFF)) {
                        controller.pulseState = BrickMode.BM_OFF;
                        break;
                    }
                }
                if(controller.pulseState.equals(BrickMode.BM_IDLE)) {
                    processController(controller);
                }

                for(Actuator actuator: controller.actuators.values()){
                    if(controller.pulseState.equals(BrickMode.BM_IDLE)) actuator.pulseState = BrickMode.BM_OFF;
                    else actuator.pulseState = controller.pulseState;
                }
            }
        }

    }


    public abstract void processController(C controller);

}