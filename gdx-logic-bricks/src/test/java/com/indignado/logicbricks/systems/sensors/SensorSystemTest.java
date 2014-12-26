package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Rubentxu
 */
public class SensorSystemTest {
    PooledEngine engine;
    private String statePruebas;
    protected boolean isActive = false;
    private SensorSystem sensorSystem;

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
        this.statePruebas = "StatePruebas";
        sensorSystem = new TestSensorSystem();
        engine.addSystem(sensorSystem);

    }


    @Test
    public void activeTest() {
        Entity player = engine.createEntity();
        Sensor sensor = new TestSensor();
        isActive = true;

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePruebas));

        player.add(stateComponent);

        engine.addEntity(player);
        engine.update(1);

        assertEquals(sensor.pulseState, BrickMode.BM_ON);
        engine.update(1);
        assertEquals(sensor.pulseState, BrickMode.BM_OFF);

    }





}
