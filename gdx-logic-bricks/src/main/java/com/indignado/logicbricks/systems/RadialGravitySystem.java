package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.indignado.logicbricks.components.RadialGravityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu
 */
public class RadialGravitySystem extends IteratingSystem implements ContactListener, EntityListener {
    private String tag = this.getClass().getSimpleName();
    private ComponentMapper<RadialGravityComponent> rgm;
    private Vector2 debris_position;
    private Vector2 planet_distance;
    private float force;

    public RadialGravitySystem() {
        super(Family.all(RadialGravityComponent.class).get(), 4);
        rgm = ComponentMapper.getFor(RadialGravityComponent.class);
        planet_distance = new Vector2();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        RadialGravityComponent radialGravity = rgm.get(entity);
        Vector2 planet_position = radialGravity.attachedRigidBody.getWorldCenter();

        if (radialGravity.bodyList.size > 0) radialGravity.bodyList.first().getWorld().clearForces();
        for (Body body : radialGravity.bodyList) {

            debris_position = body.getWorldCenter();
            planet_distance.set(0, 0);
            planet_distance.add(debris_position);
            planet_distance.sub(planet_position);
            force = (float) ((radialGravity.gravity * body.getMass()) / planet_distance.len());

            if (planet_distance.len() < radialGravity.radius * radialGravity.gravityFactor) {
                planet_distance.scl(force);

                body.applyForceToCenter(planet_distance, true);

                float desiredAngle = MathUtils.atan2(-body.getLinearVelocity().x, body.getLinearVelocity().y);
                while (desiredAngle < -180 * MathUtils.degreesToRadians)
                    desiredAngle += 360 * MathUtils.degreesToRadians;
                while (desiredAngle > 180 * MathUtils.degreesToRadians)
                    desiredAngle -= 360 * MathUtils.degreesToRadians;

                body.applyTorque(desiredAngle < 0 ? planet_distance.nor().len() / 2 : -planet_distance.nor().len() / 2, true);

            }

        }

    }


    @Override
    public void beginContact(Contact contact) {
        RadialGravityComponent radialGravity;
        radialGravity = (RadialGravityComponent) contact.getFixtureA().getUserData();

        if (radialGravity != null) {
            Body body = contact.getFixtureB().getBody();
            body.setGravityScale(0);
            body.resetMassData();
            radialGravity.bodyList.add(body);
            Log.debug(tag, "Begin RadialGravity body %s", body.getPosition());
            return;

        }

        radialGravity = (RadialGravityComponent) contact.getFixtureB().getUserData();
        if (radialGravity != null) {
            Body body = contact.getFixtureA().getBody();
            body.setGravityScale(0);
            body.resetMassData();
            radialGravity.bodyList.add(body);
            Log.debug(tag, "Begin RadialGravity body %s", body.getPosition());
            return;
        }

        if (!processContactBody(contact.getFixtureA().getBody(), contact))
            processContactBody(contact.getFixtureB().getBody(), contact);


    }


    public boolean processContactBody(Body body, Contact contact) {
        Entity entity = (Entity) body.getUserData();
        if (entity.getComponent(RadialGravityComponent.class) != null) {
            contact.resetFriction();
            return true;

        }
        return false;

    }


    @Override
    public void endContact(Contact contact) {
        RadialGravityComponent radialGravity;
        radialGravity = (RadialGravityComponent) contact.getFixtureA().getUserData();

        if (radialGravity != null) {
            Body body = contact.getFixtureB().getBody();
            body.setGravityScale(1);
            body.resetMassData();
            radialGravity.bodyList.removeValue(body, true);
            Log.debug(tag, "End Contact body %s", body.getPosition());
            return;

        }

        radialGravity = (RadialGravityComponent) contact.getFixtureB().getUserData();
        if (radialGravity != null) {
            Body body = contact.getFixtureA().getBody();
            body.setGravityScale(1);
            body.resetMassData();
            radialGravity.bodyList.removeValue(body, true);
            Log.debug(tag, "End Contact body %s", body.getPosition());

        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    @Override
    public void entityAdded(Entity entity) {
        RadialGravityComponent radialGravity = entity.getComponent(RadialGravityComponent.class);
        if (radialGravity != null) createRadialGravitySensor(entity, radialGravity);

    }


    @Override
    public void entityRemoved(Entity entity) {

    }


    private void createRadialGravitySensor(Entity entity, RadialGravityComponent radialGravity) {
        Log.debug(tag, "EntityAdded add RadialGravity");
        FixtureDefBuilder fixtureBuilder = new FixtureDefBuilder();
        RigidBodiesComponents rigidBodiesComponent = entity.getComponent(RigidBodiesComponents.class);
        if (rigidBodiesComponent == null)
            throw new LogicBricksException(tag, "Failed to create RadialGravitySensor, there is no rigidBody");

        if (radialGravity.radius == 0)
            throw new LogicBricksException(tag, "radialGravity radius can not be zero");
        if (radialGravity.attachedRigidBody == null)
            radialGravity.attachedRigidBody = rigidBodiesComponent.rigidBodies.first();

        FixtureDef radialFixture = fixtureBuilder
                .circleShape(radialGravity.radius)
                .sensor()
                .build();
        radialGravity.attachedRigidBody.createFixture(radialFixture).setUserData(radialGravity);
        Log.debug(tag, "Create Fixture RadialGravitySensor");

    }

}
