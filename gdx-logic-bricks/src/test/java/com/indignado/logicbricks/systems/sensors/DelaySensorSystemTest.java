package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.LogicBrick;
import com.indignado.logicbricks.core.sensors.DelaySensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.DelaySensorBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu
 */
public class DelaySensorSystemTest extends BaseSensorSystemTest<DelaySensor, DelaySensorSystem> {


    @Override
    public void tearDown() {
        player = null;
        sensor = null;

    }


    @Override
    protected void createContext() {
        // Create Player Entity
        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        sensor = game.getBuilder(DelaySensorBuilder.class)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround = game.getBuilder(ConditionalControllerBuilder.class)
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
    public void defaultTest() {
        engine.addEntity(player);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void delayTest() {
        sensor.delay = 1.5f;
        engine.addEntity(player);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

    }


    @Test
    public void durationTest() {
        sensor.duration = 2f;
        engine.addEntity(player);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(0.5f);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(0.21f);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void delayAndDurationTest() {
        sensor.delay = 1.5f;
        sensor.duration = 1.5f;
        engine.addEntity(player);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(0.5f);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(0.5f);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void delayDurationAndPulseModeTrueTest() {
        sensor.delay = 1.5f;
        sensor.duration = 1.5f;
        sensor.pulse = Sensor.Pulse.PM_TRUE.getValue();
        engine.addEntity(player);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(0.5f);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(0.5f);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);


    }


    @Test
    public void delayDurationAndPulseModeFalseTest() {
        sensor.delay = 1.5f;
        sensor.duration = 1.5f;
        sensor.pulse = Sensor.Pulse.PM_FALSE.getValue();
        engine.addEntity(player);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(0.5f);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(0.5f);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

    }


}
