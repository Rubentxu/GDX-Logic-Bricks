package com.indignado.logicbricks.core.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.core.EntityFactory;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuator<T extends Entity> extends Actuator {
    public EntityFactory entityFactory;
    public Vector2 localPosition;
    public float angle = 0;
    public short duration = 0; // 0 = lives forever
    public Type type;

    public enum Type {AddEntity, RemoveEntity}

}
