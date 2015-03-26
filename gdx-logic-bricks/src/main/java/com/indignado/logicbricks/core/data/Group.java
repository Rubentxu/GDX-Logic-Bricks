package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.utils.Array;

/**
 * @author Rubentxu.
 */
public class Group implements Data{
    public Transform parent = null;
    public Array<Transform> children = new Array<>();


    @Override
    public void reset() {
        parent = null;
        children.clear();

    }

}
