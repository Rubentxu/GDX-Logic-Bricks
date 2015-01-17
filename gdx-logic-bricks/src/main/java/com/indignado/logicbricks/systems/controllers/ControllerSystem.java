package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.Settings;
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
                controller.pulseState = LogicBrick.BrickMode.BM_OFF;
                for (Sensor sensor : controller.sensors.values()) {
                    if (sensor.pulseState.equals(LogicBrick.BrickMode.BM_ON)) {
                        //Log.debug(tag, "Sensor %s pulseState %s isPositive %b",sensor.name, sensor.pulseState, sensor.positive);
                        controller.pulseState = BrickMode.BM_IDLE;
                    } else {
                        controller.pulseState = LogicBrick.BrickMode.BM_OFF;
                        break;
                    }
                }
                if (controller.pulseState.equals(BrickMode.BM_IDLE)) {
                    processController(controller);
                    if (controller.pulseState.equals(BrickMode.BM_ON))
                        Log.debug(tag, "Controller %s pulseState %s", controller.name, controller.pulseState);
                }
            }
        }

    }


    public abstract void processController(C controller);

}