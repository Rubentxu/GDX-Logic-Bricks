package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * @author Rubentxu.
 */
public class IdentityComponent implements Poolable, Component {
    public long uuid;
    public String tag = "Dummy";
    public short category = 0x0001;
    public short collisionMask = -1;
    public short group = 0;


    @Override
    public void reset() {
        uuid = 0L;
        tag = "Dummy";
        category = 0x0001;
        collisionMask = -1;
        group = 0;

    }

}