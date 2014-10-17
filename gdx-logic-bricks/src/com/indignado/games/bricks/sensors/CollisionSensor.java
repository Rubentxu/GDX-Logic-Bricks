package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.games.components.CollidersComponent;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    public enum CollisionType {FULL, PARTIAL}

    // Config Values
    public CollisionType collisionType;
    public CollidersComponent collider;
    public String tagFilter;

    // Signal Values
    public CollidersComponent colliderSignal;
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
                if (tagFilter != null) {
                    if (tagFilter.equals(collider.tag)) return true;
                    else return false;
                }
                return true;
            }
            if (collisionType.equals(CollisionType.PARTIAL)) {
                if (collider.equals(colliderSignal)) {
                    if (tagFilter != null) {
                        if (tagFilter.equals(collider.tag)) return true;
                        else return false;
                    }
                    return true;
                } else return false;
            }

        }
        return false;
    }

}