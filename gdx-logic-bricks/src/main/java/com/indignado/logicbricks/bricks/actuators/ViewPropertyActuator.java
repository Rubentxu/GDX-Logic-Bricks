package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.data.Property;
import com.indignado.logicbricks.data.View;

import java.util.HashMap;

/**
 * @author Rubentxu.
 */
public class ViewPropertyActuator extends PropertyActuator<View> {

    public ViewPropertyActuator() {
        properties.put("height",new Property<Float>("height",null));
        properties.put("width",new Property<Float>("width",null));
        properties.put("opacity",new Property<Integer>("opacity",null));
        properties.put("flipX",new Property<Boolean>("flipX",null));
        properties.put("flipY",new Property<Boolean>("flipY",null));
        properties.put("tint",new Property<Color>("tint",null));

    }


}
