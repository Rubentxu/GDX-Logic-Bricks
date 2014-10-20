package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.indignado.games.components.CollisionSensorsComponents;
import com.indignado.games.components.InputSensorsComponents;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class CollisionSensorsSystem extends IteratingSystem implements ContactListener {
    private ComponentMapper<CollisionSensorsComponents> sm;


    public CollisionSensorsSystem() {
        super(Family.getFor(InputSensorsComponents.class), 0);
        sm = ComponentMapper.getFor(CollisionSensorsComponents.class);


    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {

    }


    @Override
    public void beginContact(Contact contact) {

    }


    @Override
    public void endContact(Contact contact) {

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
