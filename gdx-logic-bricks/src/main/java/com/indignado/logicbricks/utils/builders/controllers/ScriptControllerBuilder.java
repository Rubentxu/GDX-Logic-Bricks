package com.indignado.logicbricks.utils.builders.controllers;

import com.indignado.logicbricks.core.Script;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class ScriptControllerBuilder extends BrickBuilder<ScriptController> {


    public ScriptControllerBuilder() {
        brick = new ScriptController();

    }

    public ScriptControllerBuilder setScript(Script script) {
        brick.script = script;
        return this;

    }


    @Override
    public ScriptController getBrick() {
        ScriptController brickTemp = brick;
        brick = new ScriptController();
        return brickTemp;

    }

}
