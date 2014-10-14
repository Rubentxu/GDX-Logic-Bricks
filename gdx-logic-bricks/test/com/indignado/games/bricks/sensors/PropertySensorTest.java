package com.indignado.games.bricks.sensors;

import org.junit.Before;

/**
 * Created on 14/10/14.
 *
 * @author Rubentxu
 */
public class PropertySensorTest {
    private PropertySensor sensor;
    private Object entity;


    @Before
    public void setup() {
        entity = new Object();
        sensor = new PropertySensor(entity);
        sensor.evaluationType= PropertySensor.EvaluationType.CHANGED;

    }










}
