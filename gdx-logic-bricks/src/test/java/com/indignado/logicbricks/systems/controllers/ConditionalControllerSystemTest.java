package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.bricks.base.BaseTest;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.LogicBrick;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Rubentxu.
 */
public class ConditionalControllerSystemTest extends BaseTest{
    private ConditionalController controller;
    private TestSensor sensor;
    private TestSensor sensor2;
    private TestSensor sensor3;
    private Entity player;


    @Before
    public void setup() {
        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        sensor = new TestSensor();
        sensor.name = "sensorPlayer";
        sensor.pulseState = LogicBrick.BrickMode.BM_ON;

        sensor2 = new TestSensor();
        sensor2.name = "sensor2Player";
        sensor2.pulseState = LogicBrick.BrickMode.BM_ON;

        sensor3 = new TestSensor();
        sensor3.name = "sensor3Player";
        sensor3.pulseState = LogicBrick.BrickMode.BM_ON;

        controller =builders.getBrickBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("playerController")
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();

        player = entityBuilder
                .addController(controller, "Default")
                .connectToSensors(sensor, sensor2, sensor3)
                .connectToActuator(actuatorTest)
                .getEntity();

    }


    @Test
    public void conditionalANDTest() {
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);


    }


    @Test
    public void conditionalAND2Test() {
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalAND3Test() {
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = false;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalAND4Test() {
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = true;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalAND5Test() {
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = false;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalAND6Test() {
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalORTest() {
        controller.op = ConditionalController.Op.OP_OR;
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalOR2Test() {
        controller.op = ConditionalController.Op.OP_OR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalOR3Test() {
        controller.op = ConditionalController.Op.OP_OR;
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = false;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalOR4Test() {
        controller.op = ConditionalController.Op.OP_OR;
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = true;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalOR5Test() {
        controller.op = ConditionalController.Op.OP_OR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalXORTest() {
        controller.op = ConditionalController.Op.OP_XOR;
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalXOR2Test() {
        controller.op = ConditionalController.Op.OP_XOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalXOR3Test() {
        controller.op = ConditionalController.Op.OP_XOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalXOR4Test() {
        controller.op = ConditionalController.Op.OP_XOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalNANDTest() {
        controller.op = ConditionalController.Op.OP_NAND;
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalNAND2Test() {
        controller.op = ConditionalController.Op.OP_NAND;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalNAND3Test() {
        controller.op = ConditionalController.Op.OP_NAND;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalNAND4Test() {
        controller.op = ConditionalController.Op.OP_NAND;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalNORTest() {
        controller.op = ConditionalController.Op.OP_NOR;
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalNOR2Test() {
        controller.op = ConditionalController.Op.OP_NOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalNOR3Test() {
        controller.op = ConditionalController.Op.OP_NOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }


    @Test
    public void conditionalNOR4Test() {
        controller.op = ConditionalController.Op.OP_NOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalXNORTest() {
        controller.op = ConditionalController.Op.OP_XNOR;
        engine.addEntity(player);

        sensor.positive = true;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalXNOR2Test() {
        controller.op = ConditionalController.Op.OP_XNOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = true;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    @Test
    public void conditionalXNOR3Test() {
        controller.op = ConditionalController.Op.OP_XNOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = true;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, controller.pulseState);

    }

    @Test
    public void conditionalXNOR4Test() {
        controller.op = ConditionalController.Op.OP_XNOR;
        engine.addEntity(player);

        sensor.positive = false;
        sensor2.positive = false;
        sensor3.positive = false;
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, controller.pulseState);

    }


    private class TestSensor extends Sensor {
    }


}