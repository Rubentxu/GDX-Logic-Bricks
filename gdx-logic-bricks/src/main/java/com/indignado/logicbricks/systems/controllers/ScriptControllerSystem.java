package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class ScriptControllerSystem extends ControllerSystem<ScriptController, ScriptControllerComponent> {


    public ScriptControllerSystem() {
        super(ScriptControllerComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        Integer state = stateMapper.get(entity).getCurrentState();
        ObjectSet<ScriptController> controllers = (ObjectSet<ScriptController>) controllerMapper.get(entity).controllers.get(state);
        if (controllers != null) {
            for (ScriptController controller : controllers) {
                processController(controller);

            }
        }

    }


    @Override
    public void processController(ScriptController controller) {
        if (controller.sensors.size == 0)
            throw new LogicBricksException("ControllerSystem", "This sensor does not have any associated sensor");
        controller.script.execute(controller, controller.sensors, controller.actuators);

    }


}
