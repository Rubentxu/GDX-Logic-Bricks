package com.indignado.logicbricks.utils;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.components.LogicBricksComponent;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksComponentBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu.
 */
public class LogicBricksComponentTest {
    private LogicBricksComponentBuilder logicBricksComponentBuilder;
    private String state;
    private String name;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.state = "BricksStatePruebas";
        logicBricksComponentBuilder = new LogicBricksComponentBuilder();

    }


    @Test
    public void logicBricksComponentSensorTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        LogicBricksComponent lbc = logicBricksComponentBuilder.createLogicBricks(name, state)
                .addSensor(alwaysSensor)
                .build();
        assertNotNull(lbc);
        assertNotNull(lbc.logicBricks.get(state));
        assertFalse(lbc.logicBricks.get(state).isEmpty());
        assertTrue(lbc.logicBricks.get(state).iterator().next().sensors.get(AlwaysSensor.class).
                contains(alwaysSensor));

    }


    @Test
    public void logicBricksComponentSensor2Test() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        LogicBricksComponent lbc = logicBricksComponentBuilder.createLogicBricks(name, state)
                .addSensor(alwaysSensor)
                .addSensor(alwaysSensor2)
                .build();
        assertNotNull(lbc);
        assertNotNull(lbc.logicBricks.get(state));
        assertFalse(lbc.logicBricks.get(state).isEmpty());
        assertTrue(lbc.logicBricks.get(state).iterator().next().sensors.get(AlwaysSensor.class).
                contains(alwaysSensor));
        assertTrue(lbc.logicBricks.get(state).iterator().next().sensors.get(AlwaysSensor.class).
                contains(alwaysSensor2));

    }


    @Test
    public void logicBricksComponentControllerTest() {
        ConditionalController conditionalController = new ConditionalController();
        LogicBricksComponent lbc = logicBricksComponentBuilder.createLogicBricks(name, state)
                .addController(conditionalController)
                .build();
        assertNotNull(lbc);
        assertNotNull(lbc.logicBricks.get(state));
        assertFalse(lbc.logicBricks.get(state).isEmpty());
        assertTrue(lbc.logicBricks.get(state).iterator().next().controllers.get(ConditionalController.class).
                contains(conditionalController));

    }


    @Test
    public void logicBricksComponentActuatorTest() {
        MessageActuator messageActuator = new MessageActuator();
        LogicBricksComponent lbc = logicBricksComponentBuilder.createLogicBricks(name, state)
                .addActuator(messageActuator)
                .build();
        assertNotNull(lbc);
        assertNotNull(lbc.logicBricks.get(state));
        assertFalse(lbc.logicBricks.get(state).isEmpty());
        assertTrue(lbc.logicBricks.get(state).iterator().next().actuators.get(MessageActuator.class).
                contains(messageActuator));

    }


    @Test
    public void logicBricksComponentTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        ConditionalController conditionalController = new ConditionalController();
        MessageActuator messageActuator = new MessageActuator();
        LogicBricksComponent lbc = logicBricksComponentBuilder.createLogicBricks(name, state)
                .addSensor(alwaysSensor)
                .addController(conditionalController)
                .addActuator(messageActuator)
                .build();
        assertNotNull(lbc);
        assertNotNull(lbc.logicBricks.get(state));
        assertFalse(lbc.logicBricks.get(state).isEmpty());
        assertTrue(lbc.logicBricks.get(state).iterator().next().sensors.get(AlwaysSensor.class).
                contains(alwaysSensor));
        assertTrue(lbc.logicBricks.get(state).iterator().next().controllers.get(ConditionalController.class).
                contains(conditionalController));
        assertTrue(lbc.logicBricks.get(state).iterator().next().actuators.get(MessageActuator.class).
                contains(messageActuator));

    }

}
