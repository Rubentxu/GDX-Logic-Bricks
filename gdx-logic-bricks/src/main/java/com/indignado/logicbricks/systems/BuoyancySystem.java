package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.B2ShapeExtensions;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class BuoyancySystem extends IteratingSystem implements ContactListener {
    private String tag = this.getClass().getSimpleName();
    private ComponentMapper<BuoyancyComponent> bm;

    private static Vector2 c1 = new Vector2();
    private static Vector2 c2 = new Vector2();
    public static final float EPSILON = 1.1920928955078125E-7f;
    private Vector2 areac = new Vector2(0,0);
    private Vector2 massc = new Vector2(0,0);
    Vector2 sc = new Vector2(0,0);
    private float area = 0;
    private float mass = 0;
    private float sarea = 0;
    private float shapeDensity = 0;

    private void resetValues() {
        c1.set(0,0);
        c2.set(0,0);
        areac.set(0,0);
        massc.set(0,0);
        sc.set(0,0);
        area = 0;
        mass = 0;
        sarea = 0;
        shapeDensity = 0;

    }

    public BuoyancySystem() {
        super(Family.all(BuoyancyComponent.class).get(), 4);
        bm = ComponentMapper.getFor(BuoyancyComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        applyToFixture(bm.get(entity));

    }


    private <T extends Shape> boolean  applyToFixture(BuoyancyComponent buoyancy) {

        for (Body body : buoyancy.bodyList) {
            if (!body.isAwake() || !body.getType().equals(BodyDef.BodyType.DynamicBody)) continue;
            resetValues();

            for(Fixture fixture : body.getFixtureList()) {
                switch (fixture.getShape().getType()) {
                    case Circle:
                        sarea = B2ShapeExtensions.computeSubmergedArea((CircleShape) fixture.getShape(), buoyancy.normal, buoyancy.offset,
                                fixture.getBody().getTransform(), sc);
                        break;

                    case Polygon:
                        sarea = B2ShapeExtensions.computeSubmergedArea((PolygonShape) fixture.getShape(), buoyancy.normal, buoyancy.offset,
                                fixture.getBody().getTransform(), sc);
                        break;

                }

                area += sarea;
                areac.x += sarea * sc.x;
                areac.y += sarea * sc.y;

                if(buoyancy.useDensity) {
                    //TODO: Expose density publicly
                    shapeDensity = fixture.getDensity();
                }
                else
                {
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
            if(area < EPSILON) continue;
            //Buoyancy
            Vector2 buoyancyForce = buoyancy.gravity.cpy().scl(-buoyancy.density * area);
            body.applyForce(buoyancyForce,massc,true);
            Log.debug(tag,"ApplyForce buoyancyForce %s", buoyancyForce);
            //Linear drag
            Vector2 dragForce = body.getLinearVelocityFromWorldPoint(areac).sub(buoyancy.velocity);
            dragForce.scl(-buoyancy.linearDrag * area);
            body.applyForce(dragForce,areac,true);
            Log.debug(tag,"ApplyForce dragforce %s", dragForce);
            //Angular drag
            //TODO: Something that makes more physical sense?
            body.applyTorque(-body.getInertia() / body.getMass() * area * body.getAngularVelocity()
                    * buoyancy.angularDrag,true);
            Log.debug(tag,"Velocity %s", body.getLinearVelocity());

        }
        return true;

    }


/*
    public float computeSubmergedArea(CircleShape shape, final Vector2 normal, float offset, Transform xf, Vector2 c) {
        Vector2 p = xf.mul(shape.getPosition());
        float l = -(normal.dot(p) - offset);

        if (l < - shape.getRadius() + EPSILON) {
            // Completely dry
            return 0;
        }
        if (l >  shape.getRadius()) {
            // Completely wet
            c.set(p);
            return MathUtils.PI *  shape.getRadius() *  shape.getRadius();
        }

        // Magic
        float r2 =  shape.getRadius() *  shape.getRadius();
        float l2 = l * l;
        float area = (float) (r2
                * (Math.asin(l /  shape.getRadius()) + MathUtils.PI / 2.0f) + l
                * Math.sqrt(r2 - l2));
        float com = (float) (-2.0f / 3.0f * Math.pow(r2 - l2, 1.5f) / area);

        c.x = p.x + normal.x * com;
        c.y = p.y + normal.y * com;
        Log.debug(tag,"computeSubmergedArea CircleShape. area %f", area);
        return area;

    }


    public float computeSubmergedArea(PolygonShape shape, Vector2 normal, float offset, Transform xf, Vector2 c) {
        //Transform plane into shape co-ordinates
        c1.set(xf.vals[Transform.COS], xf.vals[Transform.SIN]);
        c2.set(-xf.vals[Transform.SIN], xf.vals[Transform.COS]);

        normalL.set(normal.dot(c1), normal.dot(c2));
        float offsetL = offset - normal.dot(xf.getPosition());

        float[] depths = new float[MAX_POLYGON_VERTICES];
        int diveCount = 0;
        int intoIndex = -1;
        int outoIndex = -1;

        boolean lastSubmerged = false;
        int i = 0;
        for (i = 0; i < shape.getVertexCount(); ++i) {
            shape.getVertex(i, vertex);
            // determine depth of this object versus the waterline.  negative represents submerged
            depths[i] = normalL.dot(vertex) - offsetL;
            boolean isSubmerged = depths[i] <- EPSILON;
            if (i > 0) {
                if (isSubmerged) {
                    if (!lastSubmerged) {
                        intoIndex = i-1;
                        diveCount++;
                    }

                }
                else {
                    if (lastSubmerged) {
                        outoIndex = i-1;
                        diveCount++;
                    }
                }

            }
            lastSubmerged = isSubmerged;
        }

        switch(diveCount) {
            case 0:
                if (lastSubmerged) {
                    //Completely submerged
                    MassData md = new MassData();
                    computeMass(shape,md, 1.0f);
                    c.set(xf.mul(md.center.cpy()));
                    return md.mass;
                }
                else {
                    //Completely dry
                    return 0;
                }

            case 1:
                if(intoIndex==-1)
                {
                    intoIndex = shape.getVertexCount()-1;
                }
                else
                {
                    outoIndex = shape.getVertexCount()-1;
                }
                break;
        }

        int intoIndex2 = (intoIndex+1) % shape.getVertexCount();
        int outoIndex2 = (outoIndex+1) % shape.getVertexCount();

        float intoLambda = (0 - depths[intoIndex]) / (depths[intoIndex2] - depths[intoIndex]);
        float outoLambda = (0 - depths[outoIndex]) / (depths[outoIndex2] - depths[outoIndex]);

        Vector2 verIntoIndex = new Vector2();
        shape.getVertex(intoIndex,verIntoIndex);
        Vector2 verIntoIndex2 = new Vector2();
        shape.getVertex(intoIndex2,verIntoIndex2);

        Vector2 verOutoIndex = new Vector2();
        shape.getVertex(outoIndex,verOutoIndex);
        Vector2 verOutoIndex2 = new Vector2();
        shape.getVertex(outoIndex2,verOutoIndex2);

        Vector2 intoVec = new Vector2(verIntoIndex.x * (1-intoLambda) + verIntoIndex2.x * intoLambda,
            verIntoIndex.y * (1-intoLambda)+ verIntoIndex2.y * intoLambda);

        Vector2 outoVec = new Vector2(verOutoIndex.x * (1-outoLambda) + verOutoIndex2.x * outoLambda,
                verOutoIndex.y * (1-outoLambda) + verOutoIndex2.y * outoLambda);

        // Initialize accumulator
        float area = 0;
        Vector2 center = new Vector2(0,0);
        Vector2 p2b = new Vector2();
        shape.getVertex(intoIndex2,p2b);
        Vector2 p3 = new Vector2();

        float k_inv3 = 1.0f / 3.0f;

        // An awkward loop from intoIndex2+1 to outIndex2
        i = intoIndex2;
        while (i != outoIndex2)
        {
            i = (i+1) % shape.getVertexCount();
            if (i == outoIndex2)
                p3 = outoVec;
            else
                shape.getVertex(i,p3);

            // Add the triangle formed by intoVec,p2,p3
            {
                Vector2 e1 = p2b.sub(intoVec);
                Vector2 e2 = p3.sub(intoVec);

                float D = e1.crs(e2);

                float triangleArea = 0.5f * D;

                area += triangleArea;

                // Area weighted centroid
                center.x += triangleArea * k_inv3 * (intoVec.x + p2b.x + p3.x);
                center.y += triangleArea * k_inv3 * (intoVec.y + p2b.y + p3.y);
            }
            //
            p2b = p3;
        }

        // Normalize and transform centroid
        center.x *= 1.0f / area;
        center.y *= 1.0f / area;

        c.set(xf.mul(center.cpy()));
        Log.debug(tag,"computeSubmergedArea PolygonShape. area %f", area);
        return area;

    }


    public void computeMass(PolygonShape shape, MassData massData, float density) {

        final Vector2 center = new Vector2(0.0f, 0.0f);
        float area = 0.0f;
        float I = 0.0f;

        // pRef is the reference point for forming triangles.
        // It's location doesn't change the result (except for rounding error).
        final Vector2 pRef = new Vector2(0.0f, 0.0f);

        final float k_inv3 = 1.0f / 3.0f;

        final Vector2 e1 = new Vector2();
        final Vector2 e2 = new Vector2();

        for (int i = 0; i < shape.getVertexCount(); ++i) {
            // Triangle vertices.
            final Vector2 p1 = pRef;
            Vector2 p2 = new Vector2();
            shape.getVertex(i, p2);
            Vector2 p3 = new Vector2();
            if(i + 1 < shape.getVertexCount() ) shape.getVertex(i+1,p3);
            else shape.getVertex(0,p3);

            e1.set(p2);
            e1.sub(p1);

            e2.set(p3);
            e2.sub(p1);

            final float D = e1.crs(e2);

            final float triangleArea = 0.5f * D;
            area += triangleArea;

            // Area weighted centroid
            center.x += triangleArea * k_inv3 * (p1.x + p2.x + p3.x);
            center.y += triangleArea * k_inv3 * (p1.y + p2.y + p3.y);

            final float px = p1.x, py = p1.y;
            final float ex1 = e1.x, ey1 = e1.y;
            final float ex2 = e2.x, ey2 = e2.y;

            final float intx2 = k_inv3 * (0.25f * (ex1*ex1 + ex2*ex1 + ex2*ex2) + (px*ex1 + px*ex2)) + 0.5f*px*px;
            final float inty2 = k_inv3 * (0.25f * (ey1*ey1 + ey2*ey1 + ey2*ey2) + (py*ey1 + py*ey2)) + 0.5f*py*py;

            I += D * (intx2 + inty2);
        }

        // Total mass
        massData.mass = density * area;

        // Center of mass
        assert(area > EPSILON);
        center.scl(1.0f / area);
        massData.center.set(center);

        // Inertia tensor relative to the local origin.
        massData.I = I*density;

    }*/

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
