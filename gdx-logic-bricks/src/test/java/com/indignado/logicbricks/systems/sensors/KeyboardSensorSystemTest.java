package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.CollisionSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Rubentxu
 */
public class KeyboardSensorSystemTest extends BaseSensorSystemTest<KeyboardSensor, KeyboardSensorSystem> {

    public KeyboardSensorSystemTest() {
        super();
        sensorSystem = new KeyboardSensorSystem();
        engine.addSystem(sensorSystem);
        engine.addEntityListener(sensorSystem);

    }


    @Override
    protected void setup() {
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

        sensor = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A)
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
    public void keyBoardSensorKeyTypedEventTest() {
        engine.addEntity(player);
        sensorSystem.keyTyped('a');
        sensorSystem.keyDown(Input.Keys.A);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


    @Test
    public void keyBoardSystemAllKeysConfigTest() {
        engine.addEntity(player);
        sensor.keyCode = Input.Keys.UNKNOWN;
        sensor.allKeys = true;
        sensorSystem.keyTyped('a');
        sensorSystem.keyDown(Input.Keys.A);

        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        assertTrue(sensor.positive);

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
