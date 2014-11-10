package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.data.Property;

import java.util.HashMap;

/**
 * @author Rubentxu.
 */
public class RigidBodyPropertyActuator extends PropertyActuator<Body> {


    public RigidBodyPropertyActuator() {
        properties.put("active",new Property<Boolean>("active",null));
        properties.put("awake",new Property<Boolean>("awake",null));
        properties.put("friction",new Property<Float>("friction",null));
        properties.put("restitution",new Property<Float>("restitution",null));

    }


}
