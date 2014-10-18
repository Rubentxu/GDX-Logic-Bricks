package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 14/10/14.
 *
 * @author Rubentxu
 */
public class PropertySensorTest {
    private PropertySensor sensor;
    private Entity entity;


    @Before
    public void setup() {
        entity = new Entity();
        sensor = new PropertySensor(entity);
        sensor.propertyName = "Sensor Property";
        sensor.value = 5;

    }


    @Test
    public void changedPropertyTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.CHANGED;
        sensor.valueSignal = 4;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

        isActive = sensor.isActive();
        assertFalse(isActive);
        assertEquals(sensor.value,sensor.valueSignal);

    }


    @Test
    public void notChangedPropertyTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.CHANGED;
        sensor.valueSignal= 5;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void equalsEvaluationTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.EQUAL;
        sensor.valueSignal= 5;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void equalsEvaluationFalseTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.EQUAL;
        sensor.valueSignal= 4;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void notEqualsEvaluationTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.NOT_EQUAL;
        sensor.valueSignal= 4;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void notEqualsEvaluationFalseTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.NOT_EQUAL;
        sensor.valueSignal= 5;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void greaterThanEvaluationTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.GREATER_THAN;
        sensor.valueSignal= 6;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void greaterThanEvaluationFalseTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.GREATER_THAN;
        sensor.valueSignal= 4;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void lessThanEvaluationTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.LESS_THAN;
        sensor.valueSignal= 4;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void lessThanEvaluationFalseTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.LESS_THAN;
        sensor.valueSignal= 6;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void intervalEvaluationTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.INTERVAL;
        sensor.valueSignal= 6;
        sensor.min = 3;
        sensor.max = 6;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void intervalEvaluation2Test(){
        sensor.evaluationType= PropertySensor.EvaluationType.INTERVAL;
        sensor.valueSignal= 3;
        sensor.min = 3;
        sensor.max = 6;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void intervalEvaluation3Test(){
        sensor.evaluationType= PropertySensor.EvaluationType.INTERVAL;
        sensor.valueSignal= 5;
        sensor.min = 3;
        sensor.max = 6;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void intervalEvaluationSignalGreaterTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.INTERVAL;
        sensor.valueSignal= 7;
        sensor.min = 3;
        sensor.max = 6;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void intervalEvaluationSignalLessTest(){
        sensor.evaluationType= PropertySensor.EvaluationType.INTERVAL;
        sensor.valueSignal= 2;
        sensor.min = 3;
        sensor.max = 6;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


}
