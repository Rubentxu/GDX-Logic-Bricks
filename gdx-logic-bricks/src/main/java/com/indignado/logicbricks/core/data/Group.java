package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.utils.Array;

/**
 * @author Rubentxu.
 */
public class Group implements Data{
    public Transform parent = null;
    public Array<Transform> childrens = new Array<>();

    public Group(Transform parent, Transform ...childrens) {
        this.parent = parent;
        for (Transform child : childrens) this.childrens.add(child);

    }


    @Override
    public void reset() {
        parent = null;
        childrens.clear();

    }

}
