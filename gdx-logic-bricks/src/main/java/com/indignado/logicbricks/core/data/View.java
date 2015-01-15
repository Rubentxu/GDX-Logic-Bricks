package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.core.Data;

/**
 * @author Rubentxu.
 */
public abstract class View implements Data {
    public String name;
    public Transform attachedTransform;
    public Vector2 position = new Vector2();
    public Vector2 localPosition;
    public float rotation = 0;
    public int opacity = 1;
    public int layer;
    public Color tint;


    public View setName(String name) {
        this.name = name;
        return this;

    }


    public View setAttachedTransform(Transform attachedTransform) {
        this.attachedTransform = attachedTransform;
        return this;

    }


    public View setPosition(Vector2 position) {
        setPosition(position.x, position.y);
        return this;

    }

    public View setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        return this;

    }


    public View setLocalPosition(Vector2 localPosition) {
        this.localPosition = localPosition;
        return this;

    }


    public View setRotation(float rotation) {
        this.rotation = rotation;
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
        attachedTransform = null;
        position.set(0, 0);
        localPosition = null;
        rotation = 0;
        opacity = 1;
        layer = 0;
        tint = null;

    }

}
