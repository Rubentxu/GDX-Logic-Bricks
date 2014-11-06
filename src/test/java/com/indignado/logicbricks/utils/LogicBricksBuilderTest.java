package com.indignado.logicbricks.utils;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.LogicBricks;
import com.indignado.logicbricks.bricks.actuators.MotionActuator;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class LogicBricksBuilderTest {
    private LogicBricksBuilder logicBricksBuilder;
    private String name;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        logicBricksBuilder = new LogicBricksBuilder(name);

    }


    @Test
    public void addSensorTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        LogicBricks logicBricks = logicBricksBuilder.addSensor(alwaysSensor).build();
        assertTrue(logicBricks.sensors.containsKey(AlwaysSensor.class));

    }


    @Test
    public void addControllerTest() {
        ConditionalController controller = new ConditionalController();
        LogicBricks logicBricks = logicBricksBuilder.addController(controller).build();
        assertTrue(logicBricks.controllers.containsKey(ConditionalController.class));

    }


    @Test
    public void addActuatorTest() {
        MotionActuator motionActuator = new MotionActuator();
        LogicBricks logicBricks = logicBricksBuilder.addActuator(motionActuator).build();
        assertTrue(logicBricks.actuators.containsKey(MotionActuator.class));

    }

}
