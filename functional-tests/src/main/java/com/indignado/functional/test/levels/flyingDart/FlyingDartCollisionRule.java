package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;

/**
 * @author Rubentxu.
 */
public class FlyingDartCollisionRule implements ContactListener {
    public Array<WeldJointDef> jointDefs = new Array<>();
    public Array<Joint> destroyJoints = new Array<>();

    @Override
    public void beginContact(Contact contact) {
        Vector2 contactPoint;
        WeldJointDef weldJointDef;
        if (contact.isTouching()) {

            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();

            IdentityComponent identityA = ((Entity) bodyA.getUserData()).getComponent(IdentityComponent.class);
            IdentityComponent identityB = ((Entity) bodyB.getUserData()).getComponent(IdentityComponent.class);

            Gdx.app.log("CollisionRule","Contact BodyA " + identityA.tag + " BodyB " + identityB.tag);

            BlackBoardComponent contextA = ((Entity) bodyA.getUserData()).getComponent(BlackBoardComponent.class);
            BlackBoardComponent contextB = ((Entity) bodyB.getUserData()).getComponent(BlackBoardComponent.class);

            if (identityA.tag == "Dart" && identityB.tag == "Dart") {
                for (JointEdge j : bodyA.getJointList()) {
                    destroyJoints.add(j.joint);
                    contextA.setValueProperty("freeFlight", true);
                }
                for (JointEdge j : bodyB.getJointList()) {
                    destroyJoints.add(j.joint);
                    contextB.setValueProperty("freeFlight", true);
                }
            }

            if (identityA.tag == "Wall" && identityB.tag == "Dart") {
                if ((Boolean) contextB.getProperty("freeFlight").value) {
                    weldJointDef = new WeldJointDef();
                    weldJointDef.initialize(bodyB, bodyA, bodyA.getWorldCenter());
                    jointDefs.add(weldJointDef);
                    contextB.setValueProperty("freeFlight", false);
                    Gdx.app.log("FlyingDartCollisionRule","Create WeldJoint");

                }
            }

            if (identityB.tag == "Wall" && identityA.tag == "Dart") {
                if ((Boolean) contextA.getProperty("freeFlight").value) {
                    weldJointDef = new WeldJointDef();
                    weldJointDef.initialize(bodyA, bodyB, bodyB.getWorldCenter());
                    jointDefs.add(weldJointDef);
                    contextA.setValueProperty("freeFlight", false);
                    Gdx.app.log("FlyingDartCollisionRule","Create WeldJoint");
                }
            }

            if (identityA.tag == "Crate" && identityB.tag == "Dart") {
                contactPoint = contact.getWorldManifold().getPoints()[0];
                if ((Boolean) contextB.getProperty("freeFlight").value && Math.round(contactPoint.x * 10) == 6) {
                    weldJointDef = new WeldJointDef();
                    weldJointDef.initialize(bodyB, bodyA, bodyA.getWorldCenter());
                    jointDefs.add(weldJointDef);
                    contextB.setValueProperty("freeFlight", false);
                    Gdx.app.log("FlyingDartCollisionRule","Create WeldJoint");
                }
            }

            if (identityB.tag == "Crate" && identityA.tag == "Dart") {
                contactPoint = contact.getWorldManifold().getPoints()[0];
                if ((Boolean) contextA.getProperty("freeFlight").value && Math.round(contactPoint.x * 10) == 6) {
                    weldJointDef = new WeldJointDef();
                    weldJointDef.initialize(bodyA, bodyB, bodyB.getWorldCenter());
                    jointDefs.add(weldJointDef);
                    contextA.setValueProperty("freeFlight", false);
                    Gdx.app.log("FlyingDartCollisionRule","Create WeldJoint");
                }
            }


        }
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
