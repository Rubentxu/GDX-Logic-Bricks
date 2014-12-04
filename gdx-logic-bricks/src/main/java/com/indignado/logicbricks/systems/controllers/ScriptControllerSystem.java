package com.indignado.logicbricks.systems.controllers;

import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.controllers.ScriptController;

/**
 * @author Rubentxu
 */
public class ScriptControllerSystem extends ControllerSystem<ScriptController, ScriptControllerComponent> {


    public ScriptControllerSystem() {
        super(ScriptControllerComponent.class);

    }


    @Override
    public void processController(ScriptController controller) {
        if (controller.sensors.size == 0)
            throw new LogicBricksException("ControllerSystem", "This sensor does not have any associated sensor");
        controller.script.execute(controller,controller.sensors, controller.actuators);

    }


}
