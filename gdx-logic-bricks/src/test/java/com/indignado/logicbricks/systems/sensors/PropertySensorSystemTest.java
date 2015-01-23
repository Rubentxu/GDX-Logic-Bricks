package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.Property;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.core.sensors.PropertySensor.EvaluationType;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.EngineUtils;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.PropertySensorBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu
 */
public class PropertySensorSystemTest extends BaseSensorSystemTest<PropertySensor, PropertySensorSystem> {

    private String propertyName;
    private BlackBoardComponent blackBoardComponent;
    private Property<Integer> property;


    public PropertySensorSystemTest() {
        super();
        sensorSystem = new PropertySensorSystem();
        engine.addSystem(sensorSystem);

    }


    @Override
    public void tearDown() {
        player = null;
        sensor = null;

    }


    @Override
    protected void createContext() {
        // Create Player Entity
        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        propertyName = "testProperty";
        property = new Property<Integer>(propertyName, 0);
        blackBoardComponent = entityBuilder.getComponent(BlackBoardComponent.class);
        blackBoardComponent.addProperty(property);

        sensor = EngineUtils.getBuilder(PropertySensorBuilder.class)
                .setProperty(propertyName)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround = EngineUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();

        player = entityBuilder
                .addController(controllerGround, "Default")
                .connectToSensor(sensor)
                .connectToActuator(actuatorTest)
                .getEntity();

    }


    @Test
    public void propertyChangedTest() {
        sensor.evaluationType = EvaluationType.CHANGED;
        engine.addEntity(player);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

        property.setValue(1);
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void propertyIntervalTest() {
        sensor.evaluationType = EvaluationType.INTERVAL;
        sensor.min = 1;
        sensor.max = 3;
        engine.addEntity(player);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

        property.setValue(1);
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        property.setValue(2);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        property.setValue(4);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);

    }


    @Test
    public void propertyNotEqualTest() {
        sensor.evaluationType = EvaluationType.NOT_EQUAL;
        sensor.value = 2;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        property.setValue(2);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);


    }


    @Test
    public void propertyEqualTest() {
        sensor.evaluationType = EvaluationType.EQUAL;
        sensor.value = 0;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        property.setValue(2);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);


    }


    @Test
    public void propertyGreaterThanTest() {
        sensor.evaluationType = EvaluationType.GREATER_THAN;
        sensor.value = 1;
        property.setValue(2);
        engine.addEntity(player);

        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        property.setValue(0);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);


    }


    @Test
    public void propertyLessThanTest() {
        sensor.evaluationType = EvaluationType.LESS_THAN;
        sensor.value = 1;
        engine.addEntity(player);

        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);
        assertTrue(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertTrue(sensor.positive);

        property.setValue(1);
        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_ON);
        assertFalse(sensor.positive);

        engine.update(1);
        assertTrue(sensor.pulseState == LogicBrick.BrickMode.BM_OFF);
        assertFalse(sensor.positive);


    }

}
