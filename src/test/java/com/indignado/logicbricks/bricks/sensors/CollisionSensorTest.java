package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.indignado.logicbricks.components.ColliderComponent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Set;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensorTest {
    private CollisionSensor sensor;


    @Before
    public void setup() {
        sensor =  new CollisionSensor(new Entity());

    }


    @Test
    public void collisionTest() {
        Contact contact = Mockito.mock(Contact.class);
        when(contact.isTouching()).thenReturn(true);

        sensor.contact = contact;
        assertTrue(sensor.isActive());

    }


    @Test
    public void collisionFalseTest() {
        Contact contact = Mockito.mock(Contact.class);
        when(contact.isTouching()).thenReturn(false);

        sensor.contact = contact;
        assertFalse(sensor.isActive());

    }


}
