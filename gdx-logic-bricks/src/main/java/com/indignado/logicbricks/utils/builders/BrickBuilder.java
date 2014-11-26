package com.indignado.logicbricks.utils.builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.indignado.logicbricks.core.LogicBrick;

/**
 * @author Rubentxu.
 */
public class BrickBuilder<T extends LogicBrick> {
    protected T brick;


    protected BrickBuilder() {
        createNewBrick();
    }


    private void createNewBrick() {
        try {
            this.brick = (T) brick.getClass().newInstance();

        } catch (Exception e) {
            Gdx.app.log("ActuatorBuilder", "Error instance sensor " + brick.getClass());
        }

    }


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


    public T getBrick() {
        T actuatorTemp = brick;
        createNewBrick();
        return actuatorTemp;

    }

}
