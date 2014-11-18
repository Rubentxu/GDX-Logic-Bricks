package com.indignado.logicbricks.bricks.controllers;

import com.badlogic.gdx.utils.Array;

/**
 * @author Rubentxu.
 */
public class ScriptController extends Controller {
    public Array<Script> scripts = new Array<Script>();


    public ScriptController addScripts(Script script) {
        this.scripts.add(script);
        return this;

    }

}
