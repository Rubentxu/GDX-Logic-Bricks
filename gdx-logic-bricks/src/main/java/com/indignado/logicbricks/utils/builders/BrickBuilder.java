package com.indignado.logicbricks.utils.builders;

import com.indignado.logicbricks.core.data.LogicBrick;

/**
 * @author Rubentxu.
 */
public abstract class BrickBuilder<T extends LogicBrick> {
    protected T brick;

    public BrickBuilder<T> setName(String name) {
        brick.name = name;
        return this;

    }


    public BrickBuilder<T> setState(int state) {
        brick.state = state;
        return this;

    }


    public abstract T getBrick();

}
