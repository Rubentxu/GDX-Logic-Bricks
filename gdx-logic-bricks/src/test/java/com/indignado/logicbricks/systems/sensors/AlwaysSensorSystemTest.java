package com.indignado.logicbricks.systems.sensors;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.MessageManager;
import com.indignado.logicbricks.core.actuators.MessageActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.MessageSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.actuators.MessageActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MessageSensorBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu
 */
public class AlwaysSensorSystemTest extends BaseSensorSystemTest<AlwaysSensor, AlwaysSensorSystem> {


    public AlwaysSensorSystemTest() {
        super();
        sensorSystem = new AlwaysSensorSystem();
        engine.addSystem(sensorSystem);

    }


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

        sensor = BricksUtils.getBuilder(AlwaysSensorBuilder.class)
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
    public void alwaysTest() {
        engine.addEntity(player);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

    }


    @Test
    public void alwaysPulseModeTrueTest() {
        sensor.pulse = Sensor.Pulse.PM_TRUE.getValue();
        engine.addEntity(player);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

    }


    @Test
    public void alwaysPulseModeTrueAndFrecuencyTest() {
        sensor.pulse = Sensor.Pulse.PM_TRUE.getValue();
        sensor.frequency = 1.2f;
        engine.addEntity(player);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1.3f);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(0.21f);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }

}
