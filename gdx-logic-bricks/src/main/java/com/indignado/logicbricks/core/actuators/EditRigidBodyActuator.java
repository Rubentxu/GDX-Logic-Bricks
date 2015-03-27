package com.indignado.logicbricks.core.actuators;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.data.RigidBody;

/**
 * @author Rubentxu.
 */
public class EditRigidBodyActuator extends Actuator {
    public RigidBody targetRigidBody;
    public boolean active = true;
    public boolean awake = true;
    public float friction = 0.2f;
    public float restitution = 0;
    public Entity target;


    @Override
    public void reset() {
        super.reset();
        targetRigidBody = null;
        active = true;
        awake = true;
        friction = 0.2f;
        restitution = 0;
        target = null;

    }

}