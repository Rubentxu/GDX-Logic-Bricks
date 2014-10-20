package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    public enum CollisionType {FULL, PARTIAL}

    // Config Values
    public CollisionType collisionType;

    public Entity targetSignal;


    public CollisionSensor(Entity owner) {
        super(owner);

    }


    @Override
    public Boolean isActive() {
        return isTap();

    }

}