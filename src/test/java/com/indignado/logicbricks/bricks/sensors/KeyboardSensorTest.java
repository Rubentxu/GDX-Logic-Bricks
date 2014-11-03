package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;
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
        sensor = new KeyboardSensor(new Entity());
        sensor.key= 'a';

    }


    @Test
    public void keyTest() {
        sensor.keysSignal.add('a');
        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void keyDownFalseTest() {
        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void keySignalNotEqualTest() {
        sensor.keysSignal.add('b');

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void allKeysTest() {
        sensor.allKeys = true;
        sensor.logToggle = true;
        sensor.keysSignal.add('H');

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

        sensor.keysSignal.add('o');

        isActive = sensor.isActive();
        assertTrue(isActive);
        assertEquals("Ho", sensor.target);

    }


}
