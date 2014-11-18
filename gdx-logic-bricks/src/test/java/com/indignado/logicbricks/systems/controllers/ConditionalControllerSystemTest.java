package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.systems.StateSystem;
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
    private String statePrueba;
    private String name;
    private Entity entity;
    private ConditionalControllerSystem conditionalControllerSystem;
    private boolean checkScript;
    private LogicBricksBuilder logicBricksBuilder;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.statePrueba = "StatePruebas";
        this.entity = new Entity();
        engine = new PooledEngine();
        conditionalControllerSystem = new ConditionalControllerSystem();
        engine.addSystem(conditionalControllerSystem);
        engine.addSystem(new StateSystem());

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePrueba));

        entity.add(stateComponent);
        engine.addEntity(entity);
        logicBricksBuilder = new LogicBricksBuilder(engine, entity);

    }


    @Test
    public void andControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor();
        alwaysSensor.pulseSignal = true;
        AlwaysSensor alwaysSensor2 = new AlwaysSensor();
        alwaysSensor2.pulseSignal = true;
        AlwaysSensor alwaysSensor3 = new AlwaysSensor();
        alwaysSensor3.pulseSignal = true;
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.setType(ConditionalController.Type.AND);

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .connectToSensor(alwaysSensor)
                .connectToSensor(alwaysSensor2)
                .connectToSensor(alwaysSensor3);

        engine.update(1);

        assertTrue(conditionalController.pulseSignal);

    }


    @Test
    public void andControllerFalseTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor();
        alwaysSensor.pulseSignal = true;
        AlwaysSensor alwaysSensor2 = new AlwaysSensor();
        alwaysSensor.pulseSignal = true;
        AlwaysSensor alwaysSensor3 = new AlwaysSensor();

        ConditionalController conditionalController = new ConditionalController();
        conditionalController.setType(ConditionalController.Type.AND);

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .connectToSensor(alwaysSensor)
                .connectToSensor(alwaysSensor2)
                .connectToSensor(alwaysSensor3);


        engine.update(1);

        assertFalse(conditionalController.pulseSignal);

    }


    @Test(expected = LogicBricksException.class)
    public void andControllerExceptionTest() {
        ConditionalController conditionalController = new ConditionalController();
        conditionalController.setType(ConditionalController.Type.AND);

        logicBricksBuilder.addController(conditionalController, statePrueba);
        engine.update(1);

    }


    @Test
    public void orControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor();
        alwaysSensor.pulseSignal = true;
        AlwaysSensor alwaysSensor2 = new AlwaysSensor();
        AlwaysSensor alwaysSensor3 = new AlwaysSensor();

        ConditionalController conditionalController = new ConditionalController();
        conditionalController.setType(ConditionalController.Type.OR);

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .connectToSensor(alwaysSensor)
                .connectToSensor(alwaysSensor2)
                .connectToSensor(alwaysSensor3);

        engine.update(1);
        assertTrue(conditionalController.pulseSignal);

    }

    @Test
    public void orControllerFalseTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor();
        AlwaysSensor alwaysSensor2 = new AlwaysSensor();

        ConditionalController conditionalController = new ConditionalController();
        conditionalController.setType(ConditionalController.Type.OR);

        logicBricksBuilder.addController(conditionalController, statePrueba)
                .connectToSensor(alwaysSensor)
                .connectToSensor(alwaysSensor2);

        engine.update(1);
        assertFalse(conditionalController.pulseSignal);

    }


}