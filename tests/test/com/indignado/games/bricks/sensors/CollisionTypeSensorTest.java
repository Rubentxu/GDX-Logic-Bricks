package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.games.components.CollidersComponents;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionTypeSensorTest {
    private CollisionSensor sensor;
    private Entity entity;
    private CollidersComponents colliderTest;

    @Before
    public void setup() {
        entity = new Entity();
        sensor = new CollisionSensor(entity);
        sensor.collisionType = CollisionSensor.CollisionType.FULL;
        colliderTest = new CollidersComponents();
        sensor.collider = colliderTest;
        sensor.colliderSignal = colliderTest;

    }


    @Test
    public void collisionTest() {
        sensor.targetSignal = entity;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void collisionTargetNotEqualsTest() {
        sensor.targetSignal = new Entity();

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void collisionPartialTest() {
        sensor.targetSignal = entity;
        sensor.collisionType = CollisionSensor.CollisionType.PARTIAL;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void collisionPartialFailTest() {
        sensor.targetSignal = entity;
        sensor.collisionType = CollisionSensor.CollisionType.PARTIAL;
        sensor.colliderSignal = new CollidersComponents();

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void collisionNullTest() {
        sensor.targetSignal = entity;
        sensor.collisionType = null;
        sensor.colliderSignal = new CollidersComponents();

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void collisionPartialTagFilterTest() {
        sensor.targetSignal = entity;
        sensor.collisionType = CollisionSensor.CollisionType.PARTIAL;
        sensor.tagFilter = "Enemy";

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void collisionPartialTagFilterFailTest() {
        sensor.targetSignal = entity;
        sensor.collisionType = CollisionSensor.CollisionType.PARTIAL;
        sensor.tagFilter = "Player";

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }




}
