package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.CategoryBitsManager;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.MessageManager;
import com.indignado.logicbricks.core.actuators.MessageActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.core.sensors.MessageSensor;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.actuators.MessageActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.CollisionSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MessageSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.MouseSensorBuilder;
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
        engine.addEntityListener(sensorSystem);

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
        sensor = BricksUtils.getBuilder(MessageSensorBuilder.class)
                .setSubject(message)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();

        messageActuator = BricksUtils.getBuilder(MessageActuatorBuilder.class)
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
        MessageDispatcher.getInstance().dispatchMessage(messageActuator, MessageManager.getMessageKey(message));

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
