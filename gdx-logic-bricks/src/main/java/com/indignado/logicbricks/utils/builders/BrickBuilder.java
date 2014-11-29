package com.indignado.logicbricks.utils.builders;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.LogicBrick;

/**
 * @author Rubentxu.
 */
public abstract class BrickBuilder<T extends LogicBrick> {
    protected T brick;

    public BrickBuilder<T> setName(String name) {
        brick.name = name;
        return this;

    }


    public BrickBuilder<T> setOwner(Entity owner) {
        brick.owner = owner;
        return this;

    }


    public BrickBuilder<T> setState(int state) {
        brick.state = state;
        return this;

    }


    public abstract T getBrick();

}
