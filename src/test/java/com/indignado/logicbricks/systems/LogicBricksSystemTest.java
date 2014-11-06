package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class LogicBricksSystemTest {
    PooledEngine engine;

    @Before
    public void setup() {
        engine = new PooledEngine();
        engine.addSystem(new LogicBricksSystem() {
            @Override
            protected void processEntity(Entity entity, float deltaTime) {

            }
        });

    }


    @Test
    public void getSensorTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor(new Entity());
        sensor.key= 'a';
        sensor.keysSignal.add('a');

        //player.add(sensorsMap);

        engine.addEntity(player);
        engine.update(1);

        assertTrue(sensor.isActive());
        engine.update(1);

        assertFalse(sensor.isActive());

    }

}
