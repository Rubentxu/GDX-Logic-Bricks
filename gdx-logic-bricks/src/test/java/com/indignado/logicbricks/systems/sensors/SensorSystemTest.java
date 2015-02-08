package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.bricks.base.BaseTest;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.data.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.sensors.Sensor;
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
    private boolean isActive = false;
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

        sensor = new TestSensor();
        sensor.state = stateComponent.getCurrentState();
        sensor.frequency = 1;

        entityBuilder.addController(new Controller(), stateTest, stateTest2)
                .connectToSensor(sensor);
        player = entityBuilder.getEntity();

    }


    @Test
    public void defaulTest() {
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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
        isActive = false;
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

        isActive = true;
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

        isActive = false;
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


    private class TestSensor extends Sensor {
    }

    private class TestSensorComponent extends SensorComponent<TestSensor> {
    }

    private class TestSensorSystem extends SensorSystem<TestSensor, TestSensorComponent> {

        public TestSensorSystem() {
            super(TestSensorComponent.class);
        }


        @Override
        protected boolean query(TestSensor sensor, float deltaTime) {
            return isActive;
        }

    }


}
