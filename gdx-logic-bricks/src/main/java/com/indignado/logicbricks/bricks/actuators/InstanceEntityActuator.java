package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuator<T extends Entity> extends Actuator {
    public Class<T> clazzInstance;
    public float duration = 0; // 0 = lives forever
    public Type type;

    public InstanceEntityActuator setType(Class<T> clazzInstance) {
        this.clazzInstance = clazzInstance;
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


    public enum Type {AddEntity, RemoveEntity}

}
