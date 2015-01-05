package com.indignado.logicbricks.core.controllers;

import com.indignado.logicbricks.core.Script;

/**
 * @author Rubentxu.
 */
public class ScriptController extends Controller {
    public Script script;

    @Override
    public void reset() {
        super.reset();
        script = null;

    }

}
