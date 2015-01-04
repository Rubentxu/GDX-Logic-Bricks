package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.core.sensors.Sensor.Pulse;
import com.indignado.logicbricks.systems.StateSystem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Rubentxu
 */
public class SensorSystemTest {
    LogicBricksEngine engine;
    private String stateTest;
    private boolean isActive = false;
    private SensorSystem sensorSystem;
    private Entity player;
    private TestSensor sensor;

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

    @Before
    public void setup() {
        engine = new LogicBricksEngine();
        this.stateTest = "StatePruebas";
        sensorSystem = new TestSensorSystem();
        engine.addSystem(sensorSystem);
        engine.addSystem(new StateSystem(null));
        player = engine.createEntity();
        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.createState(stateTest));
        player.add(stateComponent);
        RigidBodiesComponents rigidBodiesComponents = new RigidBodiesComponents();
        player.add(rigidBodiesComponents);
        sensor = new TestSensor();
        sensor.state = stateComponent.getCurrentState();
        sensor.frequency = 1;
        TestSensorComponent testSensorComponent = new TestSensorComponent();
        ObjectSet<TestSensor> sensorsStateTest = new ObjectSet<TestSensor>();
        sensorsStateTest.add(sensor);
        testSensorComponent.sensors.put(stateComponent.getCurrentState(), sensorsStateTest);
        player.add(testSensorComponent);

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





}
