package com.indignado.logicbricks.components.controllers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.controllers.Controller;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public class ControllerComponent<T extends Controller> extends Component implements Poolable {
    public IntMap<Set<T>> controllers = new IntMap<Set<T>>();


    @Override
    public void reset() {
        controllers.clear();

    }

}
