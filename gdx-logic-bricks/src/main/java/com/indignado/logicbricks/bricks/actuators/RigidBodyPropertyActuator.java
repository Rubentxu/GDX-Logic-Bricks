package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.data.Property;

/**
 * @author Rubentxu.
 */
public class RigidBodyPropertyActuator extends Actuator {
    public Array<Property> properties = new Array<Property>();

    public RigidBodyPropertyActuator() {
        properties.add(new Property<Boolean>(Property.Mode.Assign,"active",true));
        properties.add(new Property<Boolean>(Property.Mode.Assign,"awake",true));
        properties.add(new Property<Float>(Property.Mode.Assign,"friction",0.2f));
        properties.add(new Property<Float>(Property.Mode.Assign,"restitution",0f));

    }

    public Body targetRigidBody;


}
