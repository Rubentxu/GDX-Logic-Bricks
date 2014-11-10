package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author Rubentxu.
 */
public class RigidBodyPropertyActuator extends Actuator {
    public Body targetRigidBody;
    public boolean active = true;
    public boolean awake = true;
    public float friction = 0.2f;
    public float restitution = 0;
    public Entity target;


}
