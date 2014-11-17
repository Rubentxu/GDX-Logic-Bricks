package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public class EditEntityActuator extends Actuator {
    public Entity entity;
    public float duration = 0; // 0 = lives forever
    public Type type;
    public enum Type {AddEntity, RemoveEntity}


}
