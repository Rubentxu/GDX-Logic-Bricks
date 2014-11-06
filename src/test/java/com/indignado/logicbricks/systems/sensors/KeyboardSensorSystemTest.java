package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.KeyboardSensorComponent;
import com.indignado.logicbricks.components.LogicBricksComponent;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.View;
import com.indignado.logicbricks.systems.sensors.KeyboardSensorSystem;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksComponentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorSystemTest {
    PooledEngine engine;
    private String name;
    private String state;
    private KeyboardSensorSystem inputSensorSystem;


    @Before
    public void setup() {
        engine = new PooledEngine();
        inputSensorSystem = new KeyboardSensorSystem();
        engine.addSystem(inputSensorSystem);
        this.name = "BricksPruebas";
        this.state = "StatePruebas";

    }


    @Test
    public void keyBoardSensorKeyTypedEventTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor(new Entity());
        sensor.key= 'a';

        Set<KeyboardSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        KeyboardSensorComponent keyboardSensorComponent = new KeyboardSensorComponent();
        keyboardSensorComponent.keyboardSensors.put(state,sensorSet);

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        player.add(keyboardSensorComponent);
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
        sensor.allKeys= true;

        Set<KeyboardSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        KeyboardSensorComponent keyboardSensorComponent = new KeyboardSensorComponent();
        keyboardSensorComponent.keyboardSensors.put(state,sensorSet);

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        player.add(keyboardSensorComponent);
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
        sensor.allKeys= true;
        sensor.logToggle= true;

        Set<KeyboardSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        KeyboardSensorComponent keyboardSensorComponent = new KeyboardSensorComponent();
        keyboardSensorComponent.keyboardSensors.put(state,sensorSet);

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        player.add(keyboardSensorComponent);
        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');

        assertTrue(sensor.isActive());
        engine.update(1);
        inputSensorSystem.keyTyped('z');

        assertTrue(sensor.isActive());
        assertEquals("az",sensor.target);

    }


}
