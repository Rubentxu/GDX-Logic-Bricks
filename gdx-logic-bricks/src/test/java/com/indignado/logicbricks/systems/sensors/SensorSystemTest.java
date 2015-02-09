package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.bricks.base.BaseTest;
import com.indignado.logicbricks.core.bricks.base.TestSensor;
import com.indignado.logicbricks.core.bricks.base.TestSensorComponent;
import com.indignado.logicbricks.core.bricks.base.TestSensorSystem;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.data.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.sensors.Sensor.Pulse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Rubentxu
 */
public class SensorSystemTest extends BaseTest{

    private String stateTest;
    private String stateTest2;    
    private SensorSystem sensorSystem;
    private Entity player;
    private TestSensor sensor;
    private StateComponent stateComponent;

    @Before
    public void setup() {
        this.stateTest = "StatePruebas";
        this.stateTest2 = "StatePruebas2";
        engine.registerBricksClasses(TestSensor.class, TestSensorComponent.class, TestSensorSystem.class);

        // Create Player entity
        entityBuilder.initialize();
        stateComponent = entityBuilder.getComponent(StateComponent.class);
        stateComponent.changeCurrentState(stateComponent.createState(stateTest));
        stateComponent.createState(stateTest2);

        entityBuilder.getComponent(RigidBodiesComponents.class);

        engine.registerBricksClasses(TestSensor.class, TestSensorComponent.class, TestSensorSystem.class);
        sensor = new TestSensor();
        sensor.state = stateComponent.getCurrentState();
        sensor.frequency = 1;

        entityBuilder.addController(new Controller(), stateTest, stateTest2)
                .connectToSensor(sensor);
        player = entityBuilder.getEntity();

    }


    @Test
    public void defaulTest() {
        TestSensorSystem.isActive = false;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);


        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);


    }


    @Test
    public void pulseModeTrueTest() {
        TestSensorSystem.isActive = false;
        sensor.pulse = Pulse.PM_TRUE.getValue();
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);


    }


    @Test
    public void pulseModeFalseTest() {
        TestSensorSystem.isActive = false;
        sensor.pulse = Pulse.PM_FALSE.getValue();
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);


    }


    @Test
    public void pulseModeBothTest() {
        TestSensorSystem.isActive = false;
        sensor.pulse = (Pulse.PM_TRUE.getValue() | Pulse.PM_FALSE.getValue());
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);


    }


    @Test
    public void inverseTest() {
        TestSensorSystem.isActive = false;
        sensor.invert = true;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);


    }


    @Test
    public void pulseModeTrueInverTest() {
        TestSensorSystem.isActive = false;
        sensor.pulse = Pulse.PM_TRUE.getValue();
        sensor.invert = true;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);


    }


    @Test
    public void pulseModeFalseInverTest() {
        TestSensorSystem.isActive = false;
        sensor.pulse = Pulse.PM_FALSE.getValue();
        sensor.invert = true;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.positive);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);


    }


    @Test
    public void pulseModeBothInverTest() {
        TestSensorSystem.isActive = false;
        sensor.pulse = (Pulse.PM_TRUE.getValue() | Pulse.PM_FALSE.getValue());
        sensor.invert = true;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertTrue(sensor.positive);


    }


    @Test
    public void tapTest() {
        TestSensorSystem.isActive = false;
        sensor.tap = true;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);


        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);


    }


    @Test
    public void pulseModeTrueTapTest() {
        TestSensorSystem.isActive = false;
        sensor.pulse = Pulse.PM_TRUE.getValue();
        sensor.tap = true;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertFalse(sensor.positive);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_OFF, sensor.pulseState);
        assertFalse(sensor.positive);

    }


    @Test
    public void pulseChangeStateTest() {
        TestSensorSystem.isActive = false;
        sensor.frequency = 0;
        sensor.pulse = (Pulse.PM_TRUE.getValue() | Pulse.PM_FALSE.getValue());
        engine.addEntity(player);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        TestSensorSystem.isActive = true;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        stateComponent.changeCurrentState(stateComponent.getState(stateTest2));
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        TestSensorSystem.isActive = false;
        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);

        engine.update(1);
        assertEquals(BrickMode.BM_ON, sensor.pulseState);
        assertFalse(sensor.positive);


    }


}
