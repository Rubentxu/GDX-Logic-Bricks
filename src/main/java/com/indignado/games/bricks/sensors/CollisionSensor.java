package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.indignado.games.components.RigidBodiesComponents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensor extends Sensor {

    // Config Values
    public Filter filter;
    public Set<Fixture> targetFixtures = new HashSet<Fixture>();
    public Body attachedRigidbody;

    // Signal Values
    public Map<Fixture,Set<Contact>> fixtureContacts = new HashMap<Fixture,Set<Contact>>();


    public CollisionSensor(Entity owner) {
        super(owner);
        RigidBodiesComponents rbc = owner.getComponent(RigidBodiesComponents.class);
       

    }


    public Boolean isActive(){


        return false;
    }

}