package com.indignado.logicbricks.components.controllers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.controllers.Controller;

/**
 * @author Rubentxu.
 */
public class ControllerComponent<C extends Controller> extends Component implements Poolable {
    public IntMap<ObjectSet<C>> controllers = new IntMap<ObjectSet<C>>();


    @Override
    public void reset() {
        while (controllers.values().hasNext()) {
            for (C controller : controllers.values().next()) {
                controller.reset();
            }
        }
        controllers.clear();

    }

}
