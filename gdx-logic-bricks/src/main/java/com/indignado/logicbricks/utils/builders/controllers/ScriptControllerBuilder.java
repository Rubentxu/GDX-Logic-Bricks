package com.indignado.logicbricks.utils.builders.controllers;

import com.indignado.logicbricks.core.Script;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.actuators.CameraActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.controllers.ScriptController;

/**
 * @author Rubentxu.
 */
public class ScriptControllerBuilder extends ControllerBuilder<ScriptController> {


    public ScriptControllerBuilder() {
        brick = new ScriptController();

    }

    public ScriptControllerBuilder setScript(Script script) {
        brick.script = script;
        return this;
        
    }


    public ScriptControllerBuilder addActuator(Actuator actuator) {
        brick.actuators.add(actuator);
        return this;

    }


    @Override
    public ScriptController getBrick() {
        ScriptController brickTemp = brick;
        brick = new ScriptController();
        return brickTemp;

    }

}
