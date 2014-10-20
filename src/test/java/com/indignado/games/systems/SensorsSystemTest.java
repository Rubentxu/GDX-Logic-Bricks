package com.indignado.games.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.games.bricks.base.BaseTest;
import com.indignado.games.bricks.sensors.DelaySensor;
import com.indignado.games.bricks.sensors.KeyboardSensor;
import com.indignado.games.components.SensorsComponents;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class SensorsSystemTest extends BaseTest {

    PooledEngine engine;
    private SensorsComponents sensors;


    @Before
    public void setup() {
        engine = new PooledEngine();
        engine.addSystem(new SensorsSystem());
        sensors = new SensorsComponents();

    }


    @Test
    public void delaySensorSystemTest() {
        Entity player = engine.createEntity();
        DelaySensor delaySensor = new DelaySensor(player);
        delaySensor.delay = 2;
        sensors.sensors.add(delaySensor);
        player.add(sensors);

        engine.addEntity(player);
        engine.update(1);

        assertFalse(delaySensor.isActive());
        engine.update(1);

        assertTrue(delaySensor.isActive());

    }


    @Test
    public void keyBoardSensorSystemTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor(new Entity());
        sensor.key= 'a';
        sensor.keysSignal.add('a');

        player.add(sensors);

        engine.addEntity(player);
        engine.update(1);

        assertTrue(sensor.isActive());
        engine.update(1);

        assertFalse(sensor.isActive());

    }




}
