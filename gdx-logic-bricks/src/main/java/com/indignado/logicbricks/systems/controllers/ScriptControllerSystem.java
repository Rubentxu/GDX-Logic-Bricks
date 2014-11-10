package com.indignado.logicbricks.systems.controllers;

import com.indignado.logicbricks.bricks.controllers.Script;
import com.indignado.logicbricks.bricks.controllers.ScriptController;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;

import java.util.Iterator;

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
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        Iterator<Script> it = controller.scripts.iterator();
        while (it.hasNext()) {
            it.next().execute(controller.sensors);
        }

    }


}