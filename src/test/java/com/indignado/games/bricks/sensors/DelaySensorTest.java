package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class DelaySensorTest {
    private DelaySensor sensor;

    @Before
    public void setup() {
        sensor = new DelaySensor(new Entity());
    }


    @Test
    public void delayTest() {
        sensor.delay= 1.5f;
        sensor.deltaTimeSignal= 1.6f;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

        isActive = sensor.isActive();
        assertTrue(isActive);

        isActive = sensor.isActive();
        assertFalse(isActive);
    }
}
