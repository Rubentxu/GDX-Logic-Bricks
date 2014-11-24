package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * @author Rubentxu.
 */
public abstract class LogicEntity extends Entity implements Poolable {
    public boolean alive;


    public LogicEntity() {
        this.alive = false;

    }


    protected abstract void create(World world);


    public void init(float posX, float posY, float angle) {
        alive = true;
        respawned(posX, posY, angle);
    }


    protected abstract void respawned(float posX, float posY, float angle);


    @Override
    public void reset() {
        alive = false;

    }

}
