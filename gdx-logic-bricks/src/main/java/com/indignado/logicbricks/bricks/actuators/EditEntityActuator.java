package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author Rubentxu.
 */
public class EditEntityActuator extends Actuator {
    public enum Type {AddEntity, RemoveEntity}
    public Entity entity;
    public float duration = 0; // 0 = lives forever
    public Type type;


}
