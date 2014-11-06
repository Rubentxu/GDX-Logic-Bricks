package com.indignado.logicbricks.components.controllers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.controllers.ScriptController;

import java.util.Set;

/**
 * Created on 05/11/14.
 *
 * @author Rubentxu
 */
public class ScriptControllerComponent extends Component {
    public IntMap<Set<ScriptController>> scriptControllers = new IntMap<Set<ScriptController>>();

}
