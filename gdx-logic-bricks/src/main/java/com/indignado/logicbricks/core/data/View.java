package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.graphics.Color;


/**
 * @author Rubentxu.
 */
public abstract class View <T extends Transform> implements Data {
    public String name;
    public T transform;
    public int opacity = 1;
    public int layer = 0;
    public Color tint;
    public boolean visible = false;



    public View setName(String name) {
        this.name = name;
        return this;

    }



    public View setOpacity(int opacity) {
        this.opacity = opacity;
        return this;

    }


    public View setLayer(int layer) {
        this.layer = layer;
        return this;

    }


    public View setTint(Color tint) {
        this.tint = tint;
        return this;

    }


    @Override
    public void reset() {
        name = null;
        transform.reset();
        opacity = 1;
        layer = 0;
        tint = null;
        visible = false;

    }

}
