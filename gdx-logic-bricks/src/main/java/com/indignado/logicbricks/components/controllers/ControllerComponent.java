package com.indignado.logicbricks.components.controllers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.controllers.Controller;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public class ControllerComponent<T extends Controller> extends Component {
    public IntMap<Set<T>> controllers = new IntMap<Set<T>>();

}