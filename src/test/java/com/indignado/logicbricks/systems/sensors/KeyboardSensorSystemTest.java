package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.KeyboardSensorComponent;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorSystemTest {
    PooledEngine engine;
    private int statePruebas;

    private KeyboardSensorSystem inputSensorSystem;


    @Before
    public void setup() {
        engine = new PooledEngine();
        inputSensorSystem = new KeyboardSensorSystem();
        engine.addSystem(inputSensorSystem);
        this.statePruebas = 1;

    }


    @Test
    public void keyBoardSensorKeyTypedEventTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor(new Entity());
        sensor.key = 'a';

        new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.set(statePruebas);

        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');

        assertTrue(sensor.isActive());
        engine.update(1);

        assertFalse(sensor.isActive());

    }


    @Test
    public void keyBoardSystemAllKeysConfigTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor(new Entity());
        sensor.allKeys = true;

        new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.set(statePruebas);

        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');

        assertTrue(sensor.isActive());
        engine.update(1);
        inputSensorSystem.keyTyped('z');

        assertTrue(sensor.isActive());

    }


    @Test
    public void keyBoardSensorAllKeysAndLogToggleConfigTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor(new Entity());
        sensor.allKeys = true;
        sensor.logToggle = true;

        new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.set(statePruebas);

        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');

        assertTrue(sensor.isActive());
        engine.update(1);
        inputSensorSystem.keyTyped('z');

        assertTrue(sensor.isActive());
        assertEquals("az", sensor.target);

    }


}
