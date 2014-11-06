package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.controllers.Script;
import com.indignado.logicbricks.bricks.controllers.ScriptController;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.systems.controllers.ConditionalControllerSystem;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class ConditionalControllerSystemTest {
    private PooledEngine engine;
    private int statePrueba;
    private String name;
    private Entity entity;
    private ConditionalControllerSystem conditionalControllerSystem;
    private boolean checkScript;
    private LogicBricksBuilder logicBricksBuilder;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.statePrueba = 1;
        this.entity = new Entity();
        engine = new PooledEngine();
        conditionalControllerSystem = new ConditionalControllerSystem();
        engine.addSystem(conditionalControllerSystem);
        engine.addSystem(new StateSystem());

        StateComponent stateComponent = new StateComponent();
        stateComponent.set(statePrueba);

        entity.add(stateComponent);
        engine.addEntity(entity);
        logicBricksBuilder = new LogicBricksBuilder(entity);

    }


    @Test
    public void andControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor3 = new AlwaysSensor(new Entity());
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.type = ConditionalController.Type.AND;

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .connect(alwaysSensor)
                .connect(alwaysSensor2)
                .connect(alwaysSensor3);

        engine.update(1);

        assertTrue(conditionalController.pulseSignal);

    }


    @Test
    public void andControllerFalseTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor3 = new AlwaysSensor(new Entity());
        alwaysSensor.tap = true;
        alwaysSensor.initialized = true;
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.type = ConditionalController.Type.AND;

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .connect(alwaysSensor)
                .connect(alwaysSensor2)
                .connect(alwaysSensor3);


        engine.update(1);

        assertFalse(conditionalController.pulseSignal);

    }


    @Test(expected = LogicBricksException.class)
    public void andControllerExceptionTest() {
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.type = ConditionalController.Type.AND;

        logicBricksBuilder.addController(conditionalController, statePrueba);
        engine.update(1);

    }


    @Test
    public void orControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        alwaysSensor.tap = true;
        alwaysSensor.initialized = true;
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor3 = new AlwaysSensor(new Entity());
        alwaysSensor3.tap = true;
        alwaysSensor3.initialized = true;
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.type = ConditionalController.Type.OR;

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .connect(alwaysSensor)
                .connect(alwaysSensor2)
                .connect(alwaysSensor3);

        engine.update(1);
        assertTrue(conditionalController.pulseSignal);

    }

    @Test
    public void orControllerFalseTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        alwaysSensor.tap = true;
        alwaysSensor.initialized = true;
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        alwaysSensor2.tap = true;
        alwaysSensor2.initialized = true;
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.type = ConditionalController.Type.OR;

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .connect(alwaysSensor)
                .connect(alwaysSensor2);

        engine.update(1);
        assertFalse(conditionalController.pulseSignal);

    }



}