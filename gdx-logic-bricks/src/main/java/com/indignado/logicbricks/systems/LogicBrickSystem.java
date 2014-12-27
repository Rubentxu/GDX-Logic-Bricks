package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * @author Rubentxu.
 */
public abstract class LogicBrickSystem extends IteratingSystem {

    public LogicBrickSystem(Family family, int priority) {
        super(family, priority);
    }

    public LogicBrickSystem(Family family) {
        super(family);
    }


}
