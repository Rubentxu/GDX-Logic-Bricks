package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuator extends Actuator {
    public Entity entity;
    public float duration = 0; // 0 = lives forever
    public Type type;

    public enum Type {AddEntity, RemoveEntity}


    public InstanceEntityActuator setEntity(Entity entity) {
        this.entity = entity;
        return this;

    }


    public InstanceEntityActuator setDuration(float duration) {
        this.duration = duration;
        return this;

    }


    public InstanceEntityActuator setType(Type type) {
        this.type = type;
        return this;

    }

}
