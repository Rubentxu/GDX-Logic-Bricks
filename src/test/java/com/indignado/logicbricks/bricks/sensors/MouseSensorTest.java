package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.View;
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
        ViewsComponent viewsComponent = new ViewsComponent();
        View view =  new View();
        view.transform = new Transform();
        view.transform.setPosition(new Vector2(50,50));
        view.width = 100;
        view.height = 100;

        viewsComponent.views.add(view);

        entity.add(viewsComponent);
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
