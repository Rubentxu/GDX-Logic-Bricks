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
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.CollisionSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Rubentxu
 */
public class KeyboardSensorSystemTest {
    private LogicBricksEngine engine;
    private KeyboardSensorSystem inputSensorSystem;
    private EntityBuilder entityBuilder;
    private IdentityComponent identityPlayer;
    private KeyboardSensor keyboardSensorPlayer;


    @Before
    public void setup() {
        Settings.debugLevel = Logger.DEBUG;
        engine = new LogicBricksEngine();
        entityBuilder = new EntityBuilder(engine);
        inputSensorSystem = new KeyboardSensorSystem();
        engine.addSystem(inputSensorSystem);
        engine.addEntityListener(inputSensorSystem);
        //createContext();

    }

/*
    private void createContext() {
        // Create Player Entity
        entityBuilder.initialize();
        identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        keyboardSensorPlayer = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A)
                .setName("keyboardSensorPlayer")
                .getBrick();

        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();


        player = entityBuilder
                .addController(controllerGround, "Default")
                .connectToSensor(collisionSensorPlayer)
                .connectToActuator(actuatorTest)
                .getEntity();

        // Create Ground Entity
        entityBuilder.initialize();
        identityGround = entityBuilder.getComponent(IdentityComponent.class);
        identityGround.tag = "Ground";
        identityGround.category = categoryBitsManager.getCategoryBits(identityGround.tag);


        bodyGround = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .boxShape(5, 1)
                        .restitution(0f))
                .position(40, 20)
                .mass(1f)
                .type(BodyDef.BodyType.StaticBody)
                .build();

        rigidByGround = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByGround.rigidBodies.add(bodyGround);

        ground = entityBuilder.getEntity();


        engine.addEntity(ground);
    }


    @Test
    public void keyBoardSensorKeyTypedEventTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor();
        sensor.keyCode = Input.Keys.A;


        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        engine.update(1);
        assertTrue(sensor.pulseState == BrickMode.BM_OFF);

    }


    @Test
    public void keyBoardSystemAllKeysConfigTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor();
        sensor.allKeys = true;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);



        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        engine.update(1);
        inputSensorSystem.keyTyped('z');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_OFF);

    }


    @Test
    public void keyBoardSensorAllKeysAndLogToggleConfigTest() {
        Entity player = engine.createEntity();
        KeyboardSensor sensor = new KeyboardSensor();
        sensor.allKeys = true;
        sensor.logToggle = true;

        //new LogicBricksBuilder(player).addSensor(sensor, statePruebas);




        engine.addEntity(player);
        engine.update(1);
        inputSensorSystem.keyTyped('a');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_ON);
        engine.update(1);
        inputSensorSystem.keyTyped('z');
        engine.update(1);

        assertTrue(sensor.pulseState == BrickMode.BM_OFF);
        assertEquals("az", sensor.target);

    }*/


}
