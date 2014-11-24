package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Rubentxu.
 */
public abstract class LogicEntity extends Entity {
    private final Vector2 initialPosition;
    private final float initialAngle;


    protected LogicEntity(Vector2 position, float angle) {
        this.initialPosition = position;
        this.initialAngle = angle;
    }


    public abstract LogicEntity createLogicEntity(Engine engine, EntityBuilder entityBuilder, World physics);

}
