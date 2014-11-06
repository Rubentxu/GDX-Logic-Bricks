package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.data.View;
import com.indignado.logicbricks.systems.sensors.KeyboardSensorSystem;
import com.indignado.logicbricks.systems.sensors.MouseSensorSystem;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksComponentBuilder;
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
public class MouseSensorSystemTest {
    PooledEngine engine;
    private String name;
    private String state;
    private MouseSensorSystem inputSensorSystem;


    @Before
    public void setup() {
        engine = new PooledEngine();
        inputSensorSystem = new MouseSensorSystem();
        engine.addSystem(inputSensorSystem);
        this.name = "BricksPruebas";
        this.state = "StatePruebas";

    }


    @Test
    public void mouseSensorMovementEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor(new Entity());
        sensor.mouseEvent = MouseSensor.MouseEvent.MOVEMENT;

        Set<MouseSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        MouseSensorComponent mouseSensorComponent = new MouseSensorComponent();
        mouseSensorComponent.mouseSensors.put(state, sensorSet);

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        player.add(mouseSensorComponent);
        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.mouseMoved(50,50);

        assertTrue(sensor.isActive());
        engine.update(1);

        assertFalse(sensor.isActive());

    }


    @Test
    public void mouseSensorMouseOvertEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor(new Entity());
        sensor.mouseEvent = MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.target = player;

        Set<MouseSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        MouseSensorComponent mouseSensorComponent = new MouseSensorComponent();
        mouseSensorComponent.mouseSensors.put(state,sensorSet);

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(mouseSensorComponent);
        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.mouseMoved(25,25);
        assertTrue(sensor.isActive());

        engine.update(1);
        inputSensorSystem.mouseMoved(26,26);
        assertFalse(sensor.isActive());

        engine.update(1);
        assertFalse(sensor.isActive());

    }


    @Test
    public void mouseSensorWheelDownEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor(new Entity());
        sensor.mouseEvent = MouseSensor.MouseEvent.WHEEL_DOWN;

        Set<MouseSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        MouseSensorComponent mouseSensorComponent = new MouseSensorComponent();
        mouseSensorComponent.mouseSensors.put(state,sensorSet);

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(mouseSensorComponent);
        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.scrolled(5);
        assertFalse(sensor.isActive());

        engine.update(1);
        inputSensorSystem.scrolled(-5);
        assertTrue(sensor.isActive());

        engine.update(1);
        assertFalse(sensor.isActive());

    }


    @Test
    public void mouseSensorWheelUpEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor(new Entity());
        sensor.mouseEvent = MouseSensor.MouseEvent.WHEEL_UP;

        Set<MouseSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        MouseSensorComponent mouseSensorComponent = new MouseSensorComponent();
        mouseSensorComponent.mouseSensors.put(state,sensorSet);


        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(mouseSensorComponent);
        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.scrolled(-5);
        assertFalse(sensor.isActive());

        engine.update(1);
        inputSensorSystem.scrolled(5);
        assertTrue(sensor.isActive());

        engine.update(1);
        assertFalse(sensor.isActive());

    }


    @Test
    public void mouseSensorTouchDownEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor(new Entity());
        sensor.mouseEvent = MouseSensor.MouseEvent.LEFT_BUTTON;

        Set<MouseSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        MouseSensorComponent mouseSensorComponent = new MouseSensorComponent();
        mouseSensorComponent.mouseSensors.put(state,sensorSet);


        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(mouseSensorComponent);
        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        assertFalse(sensor.isActive());

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.LEFT);
        assertTrue(sensor.isActive());

        sensor.mouseEvent = MouseSensor.MouseEvent.RIGHT_BUTTON;

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.RIGHT);
        assertTrue(sensor.isActive());

        sensor.mouseEvent = MouseSensor.MouseEvent.MIDDLE_BUTTON;

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        assertTrue(sensor.isActive());

    }


    @Test
    public void mouseSensorTouchUpEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor(new Entity());
        sensor.mouseEvent = MouseSensor.MouseEvent.LEFT_BUTTON;
        sensor.target = player;

        Set<MouseSensor> sensorSet = new HashSet<>();
        sensorSet.add(sensor);

        MouseSensorComponent mouseSensorComponent = new MouseSensorComponent();
        mouseSensorComponent.mouseSensors.put(state,sensorSet);


        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(mouseSensorComponent);
        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.LEFT);
        assertTrue(sensor.isActive());

        engine.update(1);
        inputSensorSystem.touchUp(5, 5, 1, Input.Buttons.LEFT);
        assertFalse(sensor.isActive());

        sensor.mouseEvent = MouseSensor.MouseEvent.MIDDLE_BUTTON;

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        assertTrue(sensor.isActive());

        engine.update(1);
        inputSensorSystem.touchUp(5, 5, 1, Input.Buttons.MIDDLE);
        assertFalse(sensor.isActive());

        sensor.mouseEvent = MouseSensor.MouseEvent.RIGHT_BUTTON;

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.RIGHT);
        assertTrue(sensor.isActive());

        engine.update(1);
        inputSensorSystem.touchUp(5, 5, 1, Input.Buttons.RIGHT);
        assertFalse(sensor.isActive());


    }

}