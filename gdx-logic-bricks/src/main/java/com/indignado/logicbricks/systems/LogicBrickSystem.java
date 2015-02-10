package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.LogicBricksException;

/**
 * @author Rubentxu.
 */
public abstract class LogicBrickSystem extends IteratingSystem {
    protected String tag = this.getClass().getSimpleName();
    protected Game game;

    public LogicBrickSystem(Family family, int priority) {
        super(family, priority);

    }


    public LogicBrickSystem(Family family) {
        super(family);

    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        if (engine instanceof LogicBricksEngine) {
            this.game = ((LogicBricksEngine) engine).getGame();

        } else {
            throw new LogicBricksException(tag, "Error not LogicBricksEngine instance");

        }

    }

}
