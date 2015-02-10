package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.B2ShapeExtensions;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class BuoyancySystem extends LogicBrickSystem implements ContactListener {
    public static final float EPSILON = 1.1920928955078125E-7f;
    private static Vector2 c1 = new Vector2();
    private static Vector2 c2 = new Vector2();
    private ComponentMapper<BuoyancyComponent> bm;
    private Vector2 areac = new Vector2(0, 0);
    private Vector2 massc = new Vector2(0, 0);
    private Vector2 sc = new Vector2(0, 0);
    private float area = 0;
    private float mass = 0;
    private float sarea = 0;
    private float shapeDensity = 0;

    public BuoyancySystem() {
        super(Family.all(BuoyancyComponent.class).get(), 4);
        bm = ComponentMapper.getFor(BuoyancyComponent.class);

    }

    private void resetValues() {
        c1.set(0, 0);
        c2.set(0, 0);
        areac.set(0, 0);
        massc.set(0, 0);
        sc.set(0, 0);
        area = 0;
        mass = 0;
        sarea = 0;
        shapeDensity = 0;

    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        applyToFixture(bm.get(entity));

    }


    private <T extends Shape> boolean applyToFixture(BuoyancyComponent buoyancy) {

        for (Body body : buoyancy.bodyList) {
            if (!body.isAwake() || !body.getType().equals(BodyDef.BodyType.DynamicBody)) continue;
            resetValues();

            for (Fixture fixture : body.getFixtureList()) {
                switch (fixture.getShape().getType()) {
                    case Circle:
                        sarea = B2ShapeExtensions.computeSubmergedArea((CircleShape) fixture.getShape(), buoyancy.normal, buoyancy.offset,
                                fixture.getBody().getTransform(), sc);
                        break;

                    case Polygon:
                        sarea = B2ShapeExtensions.computeSubmergedArea((PolygonShape) fixture.getShape(), buoyancy.normal, buoyancy.offset,
                                fixture.getBody().getTransform(), sc);

                }

                area += sarea;
                areac.x += sarea * sc.x;
                areac.y += sarea * sc.y;

                if (buoyancy.useDensity) {
                    //TODO: Expose density publicly
                    shapeDensity = fixture.getDensity();
                } else {
                    shapeDensity = 1;
                }
                mass += sarea * shapeDensity;
                massc.x += sarea * sc.x * shapeDensity;
                massc.y += sarea * sc.y * shapeDensity;
            }
            areac.x /= area;
            areac.y /= area;

            massc.x /= mass;
            massc.y /= mass;
            if (area < EPSILON) continue;
            //Buoyancy
            Vector2 buoyancyForce = buoyancy.gravity.cpy().scl(-buoyancy.density * area);
            body.applyForce(buoyancyForce, massc, true);
            Log.debug(tag, "ApplyForce buoyancyForce %s", buoyancyForce);
            //Linear drag
            Vector2 dragForce = body.getLinearVelocityFromWorldPoint(areac).sub(buoyancy.velocity);
            dragForce.scl(-buoyancy.linearDrag * area);
            body.applyForce(dragForce, areac, true);
            Log.debug(tag, "ApplyForce dragforce %s", dragForce);
            //Angular drag
            body.applyTorque(-body.getInertia() / body.getMass() * area * body.getAngularVelocity()
                    * buoyancy.angularDrag, true);
            Log.debug(tag, "Velocity %s", body.getLinearVelocity());

        }
        return true;

    }


    @Override
    public void beginContact(Contact contact) {
        BuoyancyComponent buoyancy;
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        buoyancy = entityA.getComponent(BuoyancyComponent.class);
        if (buoyancy != null) {
            Body body = contact.getFixtureB().getBody();
            buoyancy.bodyList.add(body);
            Log.debug(tag, "Begin Contact body %s", body.getPosition());
            return;

        }

        buoyancy = entityB.getComponent(BuoyancyComponent.class);
        if (buoyancy != null) {
            Body body = contact.getFixtureA().getBody();
            buoyancy.bodyList.add(body);
            Log.debug(tag, "Begin Contact body %s", body.getPosition());

        }

    }


    @Override
    public void endContact(Contact contact) {
        BuoyancyComponent buoyancy;
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

        buoyancy = entityA.getComponent(BuoyancyComponent.class);
        if (buoyancy != null) {
            Body body = contact.getFixtureB().getBody();
            buoyancy.bodyList.removeValue(body, true);
            Log.debug(tag, "End Contact body %s", body.getPosition());
            return;

        }

        buoyancy = entityB.getComponent(BuoyancyComponent.class);
        if (buoyancy != null) {
            Body body = contact.getFixtureA().getBody();
            buoyancy.bodyList.removeValue(body, true);
            Log.debug(tag, "End Contact body %s", body.getPosition());

        }

    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
