package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public abstract class CollisionSensor extends Sensor {

    public enum CollisionType {FULL, PARTIAL}

    // Config Values
    public CollisionType collisionType;

    public Entity targetSignal;


    public CollisionSensor(Entity owner) {
        super(owner);

    }


}