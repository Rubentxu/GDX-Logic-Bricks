package com.badlogic.ashley.core;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public abstract class LogicEntity extends Entity implements Poolable {

    protected abstract void create(World world);


    protected abstract void init(float posX, float posY, float angle);


    @Override
    public void reset () {
        uuid = 0L;
        flags = 0;
        componentAdded.removeAllListeners();
        componentRemoved.removeAllListeners();
        scheduledForRemoval = false;

    }

}
