package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.core.sensors.PropertySensor.EvaluationType;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.BricksUtils;
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
        engine.addEntityListener(sensorSystem);

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

        sensor = BricksUtils.getBuilder(PropertySensorBuilder.class)
                .setProperty(propertyName)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
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


}
