package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.data.View;

/**
 * @author Rubentxu.
 */
public class ViewPropertyActuator extends Actuator {
    public View targetView;
    public float height;
    public float width;
    public int opacity;
    public boolean flipX;
    public boolean flipY;
    public int layer;
    public Color tint;

}
