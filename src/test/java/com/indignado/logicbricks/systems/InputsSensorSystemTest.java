package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.bricks.base.BaseTest;
import com.indignado.logicbricks.bricks.controllers.AndController;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;
import com.indignado.logicbricks.components.LogicBricksComponent;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.View;
import com.indignado.logicbricks.systems.sensors.InputsSensorSystem;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksComponentBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class InputsSensorSystemTest {
    PooledEngine engine;
    private String name;
    private String state;
    private InputsSensorSystem inputSensorSystem;


    @Before
    public void setup() {
        engine = new PooledEngine();
        inputSensorSystem = new InputsSensorSystem();
        engine.addSystem(inputSensorSystem);
        this.name = "BricksPruebas";
        this.state = "StatePruebas";

    }


    @Test
    public void keyBoardSensorKeyTypedEventTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor(new Entity());
        sensor.key= 'a';

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        player.add(lbc);
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

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        player.add(lbc);
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

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        player.add(lbc);
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


    @Test
    public void mouseSensorMovementEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor(new Entity());
        sensor.mouseEvent = MouseSensor.MouseEvent.MOVEMENT;

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        player.add(lbc);
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

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(lbc);
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

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(lbc);
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

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(lbc);
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

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(lbc);
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

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(sensor)
                .build();

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        View view = new View();
        view.height = 50;
        view.width = 50;
        view.transform =  new Transform(new Vector2(0,0),0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(lbc);
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
