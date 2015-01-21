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

        for (Body body : radialGravity.bodyList) {

            debris_position = body.getWorldCenter();

            // Vector that is used to calculate the distance
            // of the debris to the planet and what force
            // to apply to the debris.
            planet_distance.set(0,0);

            // Add the distance to the debris
            planet_distance.add(debris_position);

            // Subtract the distance to the planet's position
            // to get the vector between the debris and the planet.
            planet_distance.sub(planet_position);

            // Calculate the magnitude of the force to apply to the debris.
            // This is proportional to the distance between the planet and
            // the debris. The force is weaker the further away the debris.
            force = (float) ((radialGravity.gravity * body.getMass()) / planet_distance.len());


            // Check if the distance between the debris and the planet is within the reach
            // of the planet's gravitational pull.
            if (planet_distance.len() < radialGravity.radius * radialGravity.gravityFactor) {

                // Multiply the magnitude of the force to the directional vector.
                planet_distance.scl(force);

                body.applyForceToCenter(planet_distance, true);
                Log.debug(tag,"Body gravity force %s", force);
                if(force > -1.8f) {
                    float angle = MathUtils.atan2(body.getLinearVelocity().y, body.getLinearVelocity().x);
                    body.setTransform(body.getPosition(), angle);

                }

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
            radialGravity.bodyList.add(body);
            Log.debug(tag, "Begin Contact body %s", body.getPosition());
            return;

        }

        radialGravity = (RadialGravityComponent) contact.getFixtureB().getUserData();
        if (radialGravity != null) {
            Body body = contact.getFixtureA().getBody();

            body.setGravityScale(0);
            radialGravity.bodyList.add(body);
            Log.debug(tag, "Begin Contact body %s", body.getPosition());

        }

    }


    @Override
    public void endContact(Contact contact) {
        RadialGravityComponent radialGravity;
        radialGravity = (RadialGravityComponent) contact.getFixtureA().getUserData();

        if (radialGravity != null) {
            Body body = contact.getFixtureB().getBody();
            body.setGravityScale(1);
            radialGravity.bodyList.removeValue(body, true);
            Log.debug(tag, "End Contact body %s", body.getPosition());
            return;

        }

        radialGravity = (RadialGravityComponent) contact.getFixtureB().getUserData();
        if (radialGravity != null) {
            Body body = contact.getFixtureA().getBody();
            body.setGravityScale(1);
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
        Log.debug(tag, "EntityAdded add nearSensors");
        RadialGravityComponent radialGravity = entity.getComponent(RadialGravityComponent.class);
        Log.debug(tag, "Create RadialGravitySensorFixture");
        if(radialGravity != null) createRadialGravitySensor(entity, radialGravity);

    }


    @Override
    public void entityRemoved(Entity entity) {

    }


    private void createRadialGravitySensor(Entity entity, RadialGravityComponent radialGravity) {
        FixtureDefBuilder fixtureBuilder = new FixtureDefBuilder();
        RigidBodiesComponents rigidBodiesComponent = entity.getComponent(RigidBodiesComponents.class);
        if (rigidBodiesComponent == null)
            throw new LogicBricksException(tag, "Failed to create RadialGravitySensor, there is no rigidBody");

            if (radialGravity.radius == 0)
                throw new LogicBricksException(tag, "radialGravity radius can not be zero");
            if (radialGravity.attachedRigidBody == null) radialGravity.attachedRigidBody = rigidBodiesComponent.rigidBodies.first();

            FixtureDef radialFixture = fixtureBuilder
                    .circleShape(radialGravity.radius)
                    .sensor()
                    .build();
            radialGravity.attachedRigidBody.createFixture(radialFixture).setUserData(radialGravity);
            Log.debug(tag, "Create Fixture nearSensor");

    }

}
