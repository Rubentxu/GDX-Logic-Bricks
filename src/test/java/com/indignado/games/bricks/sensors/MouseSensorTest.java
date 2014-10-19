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
public class MouseSensorTest {
    private MouseSensor sensor;
    private Entity entity;

    @Before
    public void setup() {
        entity = new Entity();
        sensor = new MouseSensor(entity);
        sensor.mouseEvent= MouseSensor.MouseEvent.MOVEMENT;
    }


    @Test
    public void mouseEventTest() {
        sensor.mouseEventSignal = MouseSensor.MouseEvent.MOVEMENT;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void mouseEventSignalNotEqualsTest() {
        sensor.mouseEventSignal = MouseSensor.MouseEvent.LEFT_BUTTON;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void mouseOverTest() {
        sensor.mouseEvent= MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.mouseEventSignal = MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.targetSignal = entity;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void mouseOverTargetSignalNotEqualsTest() {
        sensor.mouseEvent= MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.mouseEventSignal = MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.targetSignal = new Entity();

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }







}
