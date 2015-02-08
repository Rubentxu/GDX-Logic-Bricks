package com.indignado.logicbricks.core.actuators;

import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.data.LogicBrick;

/**
 * @author Rubentxu.
 */
public class Actuator extends LogicBrick {
    public Array<Controller> controllers = new Array<Controller>();

    @Override
    public void reset() {
        super.reset();
        controllers.clear();

    }

}
