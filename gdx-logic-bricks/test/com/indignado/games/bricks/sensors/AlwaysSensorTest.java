package com.indignado.games.bricks.sensors;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class AlwaysSensorTest {
    private AlwaysSensor sensor;

    @Before
    public void setup() {
        sensor = new AlwaysSensor();
    }


    @Test
    public void alwaysTest() {
        sensor.tap= false;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

        isActive = sensor.isActive();
        assertTrue(isActive);


    }


    @Test
    public void alwaysTapTest() {
        sensor.tap= true;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

        isActive = sensor.isActive();
        assertFalse(isActive);

    }

}
