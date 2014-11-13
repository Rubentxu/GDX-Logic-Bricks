package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    // Config Values
    public Fixture ownerFixture;
    public Body ownerRigidBody;
    public Fixture targetFixture;
    public Body targetRigidBody;


    // Signal Values
    public Contact contact;


}