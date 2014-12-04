package com.indignado.logicbricks.core.actuators;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.EntityFactory;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuator<T extends Entity> extends Actuator {
    public EntityFactory entityFactory;
    public float duration = 0; // 0 = lives forever
    public Type type;

    public enum Type {AddEntity, RemoveEntity}

}
