package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.StateSystem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Rubentxu
 */
public class SensorSystemTest {
    PooledEngine engine;
    private String stateTest;
    private boolean isActive = false;
    private SensorSystem sensorSystem;
    private Entity player;
    private TestSensor sensor;

    private class TestSensor extends Sensor {}
    private class TestSensorComponent extends SensorComponent<TestSensor> {}
    private class TestSensorSystem extends SensorSystem<TestSensor,TestSensorComponent> {

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
        TestSensorComponent testSensorComponent = new TestSensorComponent();
        ObjectSet<TestSensor> sensorsStateTest = new ObjectSet<TestSensor>();
        sensorsStateTest.add(sensor);
        testSensorComponent.sensors.put(stateComponent.getCurrentState(),sensorsStateTest);
        player.add(testSensorComponent);

    }


    @Test
    public void defaulTest() {
        isActive = true;

        engine.addEntity(player);
        engine.update(1);

        assertEquals(sensor.pulseState, BrickMode.BM_ON);
        assertTrue(sensor.positive);
        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_OFF);
        assertTrue(sensor.positive);

    }


    @Test
    public void defaulTest2() {
        isActive = true;

        engine.addEntity(player);
        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        isActive = false;
        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_ON);
        assertFalse(sensor.positive);

    }


    @Test
    public void PulseModeTrueTest() {
        isActive = true;
        sensor.pulse = Sensor.Pulse.PM_TRUE;

        engine.addEntity(player);

        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_ON);
        assertTrue(sensor.positive);

        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_ON);
        assertTrue(sensor.positive);

        isActive = false;
        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_ON);
        assertFalse(sensor.positive);


    }


}
