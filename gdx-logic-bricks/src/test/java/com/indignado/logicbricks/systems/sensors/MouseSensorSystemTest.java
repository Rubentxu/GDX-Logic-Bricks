package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.bricks.sensors.MouseSensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.TextureView;
import com.indignado.logicbricks.data.View;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class MouseSensorSystemTest {
    PooledEngine engine;
    private String statePruebas;
    private MouseSensorSystem inputSensorSystem;


    @Before
    public void setup() {
        engine = new PooledEngine();
        inputSensorSystem = new MouseSensorSystem();
        engine.addSystem(inputSensorSystem);
        this.statePruebas = "StatePruebas";

    }


    @Test
    public void mouseSensorMovementEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor();
        sensor.mouseEvent = MouseSensor.MouseEvent.MOVEMENT;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.mouseMoved(50, 50);
        engine.update(1);

        assertTrue(sensor.pulseSignal);
        engine.update(1);

        assertFalse(sensor.pulseSignal);

    }


    @Test
    public void mouseSensorMouseOvertEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor();
        sensor.mouseEvent = MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.target = player;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        TextureView view = new TextureView();
        view.height = 50;
        view.width = 50;
        view.transform = new Transform(new Vector2(0, 0), 0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.mouseMoved(25, 25);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

        engine.update(1);
        inputSensorSystem.mouseMoved(26, 26);
        engine.update(1);
        assertFalse(sensor.pulseSignal);

        engine.update(1);
        assertFalse(sensor.pulseSignal);

    }


    @Test
    public void mouseSensorWheelDownEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor();
        sensor.mouseEvent = MouseSensor.MouseEvent.WHEEL_DOWN;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        TextureView view = new TextureView();
        view.height = 50;
        view.width = 50;
        view.transform = new Transform(new Vector2(0, 0), 0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.scrolled(5);
        engine.update(1);
        assertFalse(sensor.pulseSignal);

        engine.update(1);
        inputSensorSystem.scrolled(-5);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

        engine.update(1);
        assertFalse(sensor.pulseSignal);

    }


    @Test
    public void mouseSensorWheelUpEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor();
        sensor.mouseEvent = MouseSensor.MouseEvent.WHEEL_UP;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        TextureView view = new TextureView();
        view.height = 50;
        view.width = 50;
        view.transform = new Transform(new Vector2(0, 0), 0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.scrolled(-5);
        engine.update(1);
        assertFalse(sensor.pulseSignal);

        engine.update(1);
        inputSensorSystem.scrolled(5);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

        engine.update(1);
        assertFalse(sensor.pulseSignal);

    }


    @Test
    public void mouseSensorTouchDownEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor();
        sensor.mouseEvent = MouseSensor.MouseEvent.LEFT_BUTTON;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        TextureView view = new TextureView();
        view.height = 50;
        view.width = 50;
        view.transform = new Transform(new Vector2(0, 0), 0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        engine.update(1);
        assertFalse(sensor.pulseSignal);

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.LEFT);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

        sensor.mouseEvent = MouseSensor.MouseEvent.RIGHT_BUTTON;

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.RIGHT);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

        sensor.mouseEvent = MouseSensor.MouseEvent.MIDDLE_BUTTON;

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

    }


    @Test
    public void mouseSensorTouchUpEventTest() {
        Entity player = engine.createEntity();
        MouseSensor sensor = new MouseSensor();
        sensor.mouseEvent = MouseSensor.MouseEvent.LEFT_BUTTON;
        sensor.target = player;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        TextureView view = new TextureView();
        view.height = 50;
        view.width = 50;
        view.transform = new Transform(new Vector2(0, 0), 0);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(view);

        player.add(stateComponent);
        player.add(viewsComponent);

        engine.addEntity(player);

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.LEFT);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

        engine.update(1);
        inputSensorSystem.touchUp(5, 5, 1, Input.Buttons.LEFT);
        engine.update(1);
        assertFalse(sensor.pulseSignal);

        sensor.mouseEvent = MouseSensor.MouseEvent.MIDDLE_BUTTON;

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

        engine.update(1);
        inputSensorSystem.touchUp(5, 5, 1, Input.Buttons.MIDDLE);
        engine.update(1);
        assertFalse(sensor.pulseSignal);

        sensor.mouseEvent = MouseSensor.MouseEvent.RIGHT_BUTTON;

        engine.update(1);
        inputSensorSystem.touchDown(5, 5, 1, Input.Buttons.RIGHT);
        engine.update(1);
        assertTrue(sensor.pulseSignal);

        engine.update(1);
        inputSensorSystem.touchUp(5, 5, 1, Input.Buttons.RIGHT);
        engine.update(1);
        assertFalse(sensor.pulseSignal);


    }

}
