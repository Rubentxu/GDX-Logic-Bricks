package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorSystemTest {
    PooledEngine engine;
    private String statePruebas;

    private KeyboardSensorSystem inputSensorSystem;


    @Before
    public void setup() {
        engine = new PooledEngine();
        inputSensorSystem = new KeyboardSensorSystem();
        engine.addSystem(inputSensorSystem);
        this.statePruebas = "StatePruebas";

    }


    @Test
    public void keyBoardSensorKeyTypedEventTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor();
        sensor.keyCode = Input.Keys.A;

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_OFF);

    }


    @Test
    public void keyBoardSystemAllKeysConfigTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor();
        sensor.allKeys = true;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        engine.update(1);
        inputSensorSystem.keyTyped('z');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_OFF);

    }


    @Test
    public void keyBoardSensorAllKeysAndLogToggleConfigTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor();
        sensor.allKeys = true;
        sensor.logToggle = true;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        engine.update(1);
        inputSensorSystem.keyTyped('z');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_OFF);
        assertEquals("az", sensor.target);

    }


}
