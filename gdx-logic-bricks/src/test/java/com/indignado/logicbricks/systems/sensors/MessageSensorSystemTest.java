package com.indignado.logicbricks.systems.sensors;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.MessageHandler;
import com.indignado.logicbricks.core.actuators.MessageActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.LogicBrick;
import com.indignado.logicbricks.core.sensors.MessageSensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.actuators.MessageActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MessageSensorBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu
 */
public class MessageSensorSystemTest extends BaseSensorSystemTest<MessageSensor, MessageSensorSystem> {


    private MessageActuator messageActuator;
    private String message;


    public MessageSensorSystemTest() {
        super();
        sensorSystem = new MessageSensorSystem();
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

        message = "TestMessage";
        sensor =builders.getBrickBuilder(MessageSensorBuilder.class)
                .setSubject(message)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround =builders.getBrickBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();

        messageActuator =builders.getBrickBuilder(MessageActuatorBuilder.class)
                .setMessage(message)
                .getBrick();

        player = entityBuilder
                .addController(controllerGround, "Default")
                .connectToSensor(sensor)
                .connectToActuator(actuatorTest)
                .getEntity();

    }


    @Test
    public void messageTest() {
        engine.addEntity(player);
        MessageManager.getInstance().dispatchMessage(messageActuator, MessageHandler.getMessageKey(message));

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        sensor.managedMessage = true;
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


}
