package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.games.components.CollidersComponents;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    public enum CollisionType {FULL, PARTIAL}

    // Config Values
    public CollisionType collisionType;
    public CollidersComponents collider;
    public String tagFilter;

    // Signal Values
    public CollidersComponents colliderSignal;
    public Entity targetSignal;


    public CollisionSensor(Entity owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;

        if (collisionType == null || collider == null
                || colliderSignal == null || targetSignal == null) return false;

        if (targetSignal.equals(owner)) {
            if (collisionType.equals(CollisionType.FULL)) {
                return true;
            }
            if (collisionType.equals(CollisionType.PARTIAL)) {
                if (collider.equals(colliderSignal)) {
                    return true;
                } else return false;
            }

        }
        return false;
    }

}