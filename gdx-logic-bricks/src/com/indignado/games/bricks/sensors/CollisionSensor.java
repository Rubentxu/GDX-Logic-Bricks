package com.indignado.games.bricks.sensors;

import com.indignado.games.components.ColliderComponent;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor<T> extends Sensor {

    public enum CollisionType { FULL, PARTIAL }

    // Config Values
    public CollisionType collisionType;
    public ColliderComponent collider;

    // Signal Values
    public ColliderComponent colliderSignal;
    public T targetSignal;


    public CollisionSensor(T owner) {
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
            } else if (collisionType.equals(CollisionType.PARTIAL)) {
                if (collider.equals(colliderSignal)) return true;
                else return false;
            }


        }
        return false;
    }

}