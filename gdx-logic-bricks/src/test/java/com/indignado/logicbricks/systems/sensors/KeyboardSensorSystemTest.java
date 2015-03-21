package com.indignado.logicbricks.systems.sensors;

import com.badlogic.gdx.Input;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Rubentxu
 */
public class KeyboardSensorSystemTest extends BaseSensorSystemTest<KeyboardSensor, KeyboardSensorSystem> {



    @Override
    protected void tearDown() {

    }


    @Override
    protected void createContext() {
        // Create Player Entity
        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        sensor = builders.getBrickBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround =builders.getBrickBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();


        player = entityBuilder
                .addController(controllerGround, "Default")
                .connectToSensor(sensor)
                .connectToActuator(actuatorTest)
                .getEntity();
        sensorSystem = engine.getSystem(KeyboardSensorSystem.class);
    }


    @Test
    public void keyDownTest() {
        engine.addEntity(player);

        sensorSystem.keyTyped('a');
        sensorSystem.keyDown(Input.Keys.A);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        sensorSystem.keyUp(Input.Keys.A);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


    @Test
    public void keyDownPulseModeTrueTest() {
        sensor.pulse = Sensor.Pulse.PM_TRUE.getValue();
        engine.addEntity(player);

        sensorSystem.keyTyped('a');
        sensorSystem.keyDown(Input.Keys.A);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensorSystem.keyUp(Input.Keys.A);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertFalse(sensor.positive);

        sensorSystem.keyUp(Input.Keys.A);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_OFF);
        assertFalse(sensor.positive);
    }


    @Test
    public void keyDownPulseModeTrueAndFalseTest() {
        sensor.pulse = Sensor.Pulse.PM_TRUE.getValue() | Sensor.Pulse.PM_FALSE.getValue();
        engine.addEntity(player);

        sensorSystem.keyTyped('a');
        sensorSystem.keyDown(Input.Keys.A);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensorSystem.keyUp(Input.Keys.A);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertFalse(sensor.positive);

        sensorSystem.keyUp(Input.Keys.A);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertFalse(sensor.positive);

        sensorSystem.keyUp(Input.Keys.A);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


    @Test
    public void keyDownAllKeysConfigTest() {
        engine.addEntity(player);

        sensor.keyCode = Input.Keys.UNKNOWN;
        sensor.allKeys = true;
        sensorSystem.keyTyped('a');
        sensorSystem.keyDown(Input.Keys.A);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

        sensorSystem.keyUp(Input.Keys.A);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


    @Test
    public void keyBoardSensorAllKeysAndLogToggleConfigTest() {
        engine.addEntity(player);

        sensor.keyCode = Input.Keys.UNKNOWN;
        sensor.allKeys = true;
        sensor.logToggle = true;
        sensorSystem.keyTyped('a');

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertFalse(sensor.positive);

        sensorSystem.keyTyped('z');
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);
        assertEquals("az", sensor.target);

    }


}
