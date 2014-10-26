package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.games.components.TextureBoundsComponent;
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
        TextureBoundsComponent bounds = new TextureBoundsComponent();
        bounds.bounds.set(50,50, 100, 100);
        entity.add(bounds);
        sensor = new MouseSensor(entity);
        sensor.mouseEvent= MouseSensor.MouseEvent.MOVEMENT;
    }


    @Test
    public void mouseEventTest() {
        sensor.mouseEventSignal = true;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void mouseOverTest() {
        sensor.mouseEvent= MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.mouseEventSignal = true;
        sensor.positionXsignal = 51;
        sensor.positionYsignal = 51;
        sensor.target = entity;

        Boolean isActive = sensor.isActive();
        assertTrue(isActive);

    }


    @Test
    public void notMouseOverTest() {
        sensor.mouseEvent= MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.mouseEventSignal = true;
        sensor.positionXsignal = 151;
        sensor.positionYsignal = 151;
        sensor.target = entity;

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


    @Test
    public void mouseOverTargetSignalNotEqualsTest() {
        sensor.mouseEvent= MouseSensor.MouseEvent.MOUSE_OVER;
        sensor.mouseEventSignal = true;
        sensor.target = new Entity();

        Boolean isActive = sensor.isActive();
        assertFalse(isActive);

    }


}
