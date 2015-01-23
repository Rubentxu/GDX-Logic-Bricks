package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.utils.EngineUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Rubentxu
 */
public class ControllerSystemTest {
    LogicBricksEngine engine;
    private String stateTest;
    private ControllerSystem controllerSystem;
    private Controller controller;
    private AlwaysSensor sensor;
    private Entity player;
    private EntityBuilder entityBuilder;


    public ControllerSystemTest() {
        engine = new LogicBricksEngine();
        entityBuilder = new EntityBuilder(engine);
        controllerSystem = new TestControllerSystem();
        engine.addSystem(controllerSystem);

    }


    @Before
    public void setup() {
        this.stateTest = "StatePruebas";

        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        StateComponent stateComponent = entityBuilder.getComponent(StateComponent.class);
        stateComponent.createState(stateTest);

        sensor = EngineUtils.getBuilder(AlwaysSensorBuilder.class)
                .setName("sensorPlayer")
                .getBrick();


        controller = new Controller();
        controller.name = "TestController";
        controller.sensors.put(sensor.name, sensor);
        ObjectSet<Controller> controllersSet = new ObjectSet<>();
        controllersSet.add(controller);
        ControllerComponent controllerComponent = entityBuilder.getComponent(ControllerComponent.class);
        controllerComponent.controllers.put(stateComponent.getCurrentState(), controllersSet);


        player = entityBuilder.getEntity();

    }


    @Test
    public void defaulTest() {
        sensor.pulseState = BrickMode.BM_ON;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, controller.pulseState);

        sensor.pulseState = BrickMode.BM_IDLE;
        engine.update(1);
        assertEquals(BrickMode.BM_OFF, controller.pulseState);

        sensor.pulseState = BrickMode.BM_ON;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, controller.pulseState);

        sensor.pulseState = BrickMode.BM_OFF;
        engine.update(1);
        assertEquals(BrickMode.BM_OFF, controller.pulseState);


    }


    @Test
    public void twoSensorsTest() {
        AlwaysSensor sensor2 = EngineUtils.getBuilder(AlwaysSensorBuilder.class)
                .setName("sensor2Player")
                .getBrick();

        controller.sensors.put(sensor2.name, sensor2);
        sensor.pulseState = BrickMode.BM_ON;
        sensor2.pulseState = BrickMode.BM_ON;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, controller.pulseState);

        sensor.pulseState = BrickMode.BM_IDLE;
        sensor2.pulseState = BrickMode.BM_ON;
        engine.update(1);
        assertEquals(BrickMode.BM_OFF, controller.pulseState);

        sensor.pulseState = BrickMode.BM_ON;
        sensor2.pulseState = BrickMode.BM_OFF;
        engine.update(1);
        assertEquals(BrickMode.BM_OFF, controller.pulseState);

        sensor.pulseState = BrickMode.BM_OFF;
        sensor2.pulseState = BrickMode.BM_ON;
        engine.update(1);
        assertEquals(BrickMode.BM_OFF, controller.pulseState);


    }


    private class TestControllerSystem extends ControllerSystem<Controller, ControllerComponent> {

        public TestControllerSystem() {
            super(ControllerComponent.class);
        }


        @Override
        public void processController(Controller controller) {
            controller.pulseState = BrickMode.BM_ON;

        }
    }


}
