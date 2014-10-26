package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.games.components.TextureBoundsComponent;
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
public class PhysicsCollisionSensorTest {
    private PhysicsCollisionSensor sensor;
    private Entity entity;
    private World physic;
    private Set<TextureBoundsComponent> fixtureTest;
    private TextureBoundsComponent boundsTest;

    @Before
    public void setup() {
        GdxNativesLoader.load();
        physic = new World(new Vector2(0, -9.81f), true);
        entity = new Entity();
        sensor = new PhysicsCollisionSensor(entity);
        sensor.collisionType = CollisionSensor.CollisionType.FULL;

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
