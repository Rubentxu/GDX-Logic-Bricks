package com.indignado.logicbricks.core.actuators;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuator<T extends Entity> extends Actuator {
    public Class<T> clazzInstance;
    public float duration = 0; // 0 = lives forever
    public Type type;

    public enum Type {AddEntity, RemoveEntity}

}
