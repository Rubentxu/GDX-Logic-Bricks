package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.indignado.games.components.BoundsComponent;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class BoundsCollisionSensorTest {
    private BoundsCollisionSensor sensor;
    private Entity entity;
    private Set<BoundsComponent> collidersTest;
    private BoundsComponent boundsTest;

    @Before
    public void setup() {
        entity = new Entity();
        sensor = new BoundsCollisionSensor(entity);
        sensor.collisionType = CollisionSensor.CollisionType.FULL;
        collidersTest =  new HashSet<BoundsComponent>();
        BoundsComponent bounds = new BoundsComponent();
        bounds.bounds = new Rectangle(50,50,100,100);
        collidersTest.add(bounds);
        sensor.colliders = collidersTest;
        BoundsComponent boundCollider = new BoundsComponent();
        boundCollider.bounds = new Rectangle(80,80,100,100);
        sensor.colliderSignal = boundCollider;

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





}
