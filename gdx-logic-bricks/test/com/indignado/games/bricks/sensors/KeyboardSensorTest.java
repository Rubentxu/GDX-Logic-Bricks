package com.indignado.games.bricks.sensors;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensorTest {
    private KeyboardSensor sensor;

    @Before
    public void setup() {
        sensor = new KeyboardSensor();
        sensor.key= 'a';
    }


    @Test
    public void keyTest() {
        sensor.keyDownSignal= true;
        sensor.keySignal = 'a';

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void keyDownFalseTest() {
        sensor.keyDownSignal= false;
        sensor.keySignal = 'a';

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void keySignalNotEqualTest() {
        sensor.keyDownSignal= true;
        sensor.keySignal = 'b';

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void allKeysTest() {
        sensor.keyDownSignal= true;
        sensor.allKeys = true;
        sensor.logToggle = true;
        sensor.keySignal = 'H';

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

        sensor.keySignal = 'o';

        isActive = sensor.isActive();
        assertTrue(isActive);
        assertEquals("Ho", sensor.target);



    }




}
