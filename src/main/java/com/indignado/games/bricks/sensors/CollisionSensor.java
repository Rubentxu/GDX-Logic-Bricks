package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.indignado.games.components.BoundsComponent;
import com.indignado.games.components.CollidersComponents;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    public enum CollisionType {FULL, PARTIAL}

    // Config Values
    public CollisionType collisionType;
    public BoundsComponent collider;

    public Filter filter;
    public Set<Fixture> fixtures = new HashSet<Fixture>();
    public Body attachedRigidbody;

    // Signal Values
    public boolean colliderSignal;
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