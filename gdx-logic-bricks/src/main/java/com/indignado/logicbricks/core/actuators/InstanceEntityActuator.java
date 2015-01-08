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
    public Vector2 initialVelocity;
    public float initialAngularVelocity = 0;
    public float duration = 0; // 0 = lives forever
    public Type type;


    @Override
    public void reset() {
        super.reset();
        entityFactory = null;
        localPosition = null;
        angle = 0;
        initialVelocity = null;
        initialAngularVelocity = 0;
        duration = 0;
        type = null;

    }

    public enum Type {AddEntity, RemoveEntity}

}
