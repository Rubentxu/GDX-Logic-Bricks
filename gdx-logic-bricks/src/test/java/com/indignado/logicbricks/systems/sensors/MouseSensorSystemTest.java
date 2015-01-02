package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MouseSensorBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Rubentxu
 */
public class MouseSensorSystemTest extends BaseSensorSystemTest<MouseSensor, MouseSensorSystem> {


    public MouseSensorSystemTest() {
        super();
        sensorSystem = new MouseSensorSystem(null);
        engine.addSystem(sensorSystem);
        engine.addEntityListener(sensorSystem);
    }


    @Override
    public void setup() {

    }


    @Override
    protected void tearDown() {

    }


    @Override
    protected void createContext() {
        // Create Player Entity
        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        sensor = BricksUtils.getBuilder(MouseSensorBuilder.class)
                .setMouseEvent(MouseSensor.MouseEvent.MOVEMENT)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();

        player = entityBuilder
                .addController(controllerGround, "Default")
                .connectToSensor(sensor)
                .connectToActuator(actuatorTest)
                .getEntity();

    }


    @Test
    public void mouseSensorMovementEventTest() {
        engine.addEntity(player);

        sensorSystem.mouseMoved(50, 50);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


    @Test
    public void mouseSensorMouseOvertEventTest() {
        sensor.mouseEvent = (MouseSensor.MouseEvent.MOUSE_OVER);
        sensor.target = (player);
        engine.addEntity(player);

        sensorSystem.mouseMoved(25, 25);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensorSystem.mouseMoved(26, 26);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


    @Test
    public void mouseSensorWheelDownEventTest() {
        sensor.mouseEvent = MouseSensor.MouseEvent.WHEEL_UP;
        engine.addEntity(player);

        sensorSystem.scrolled(5);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void mouseSensorWheelUpEventTest() {
        engine.addEntity(player);
        sensor.mouseEvent = (MouseSensor.MouseEvent.WHEEL_UP);

        sensorSystem.scrolled(-5);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);


        sensorSystem.scrolled(5);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

    }


    @Test
    public void mouseSensorTouchDownEventTest() {
        engine.addEntity(player);
        sensor.mouseEvent = (MouseSensor.MouseEvent.LEFT_BUTTON_DOWN);

        sensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensorSystem.touchDown(5, 5, 1, Input.Buttons.LEFT);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensor.mouseEvent = (MouseSensor.MouseEvent.RIGHT_BUTTON_DOWN);

        sensorSystem.touchDown(5, 5, 1, Input.Buttons.RIGHT);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensor.mouseEvent = (MouseSensor.MouseEvent.MIDDLE_BUTTON_DOWN);

        sensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

    }


    @Test
    public void mouseSensorTouchUpEventTest() {
        engine.addEntity(player);
        sensor.mouseEvent = (MouseSensor.MouseEvent.LEFT_BUTTON_UP);
        sensor.target = (player);

        sensorSystem.touchDown(5, 5, 1, Input.Buttons.LEFT);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.LEFT);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensor.mouseEvent = (MouseSensor.MouseEvent.MIDDLE_BUTTON_UP);

        sensorSystem.touchDown(5, 5, 1, Input.Buttons.MIDDLE);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.MIDDLE);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensor.mouseEvent = (MouseSensor.MouseEvent.RIGHT_BUTTON_UP);

        sensorSystem.touchDown(5, 5, 1, Input.Buttons.RIGHT);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensorSystem.touchUp(5, 5, 1, Input.Buttons.RIGHT);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertTrue(sensor.positive);

    }

}
