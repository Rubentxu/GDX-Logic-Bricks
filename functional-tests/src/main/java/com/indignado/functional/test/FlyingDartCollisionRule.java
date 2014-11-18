package com.indignado.functional.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.BlackBoardComponent;

/**
 * @author Rubentxu.
 */
public class FlyingDartCollisionRule implements ContactListener {
    public Array<WeldJointDef> jointDefs = new Array<>();

    @Override
    public void beginContact(Contact contact) {
        Vector2 contactPoint;
        WeldJointDef weldJointDef;
        if (contact.isTouching()) {
            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();
            if (bodyA.getUserData() instanceof BlackBoardComponent && bodyB.getUserData() instanceof BlackBoardComponent) {
                BlackBoardComponent contextA = (BlackBoardComponent) bodyA.getUserData();
                BlackBoardComponent contextB = (BlackBoardComponent) bodyB.getUserData();
                String nameA = (String) contextA.getProperty("type").value;
                String nameB = (String) contextB.getProperty("type").value;

                if (nameA == "arrow" && nameB == "arrow") {
                    for (JointEdge j : bodyA.getJointList()) {
                        bodyA.getWorld().destroyJoint(j.joint);
                    }
                    for (JointEdge j : bodyB.getJointList()) {
                        bodyB.getWorld().destroyJoint(j.joint);
                    }
                }

                if (nameA == "wall" && nameB == "arrow") {
                    if (!(Boolean) contextB.getProperty("freeFlight").value) {
                        weldJointDef = new WeldJointDef();
                        weldJointDef.initialize(bodyB, bodyA, bodyA.getWorldCenter());
                        jointDefs.add(weldJointDef);
                        //bodyB.getWorld().createJoint(weldJointDef);
                    }
                }

                if (nameB == "wall" && nameA == "arrow") {
                    if (!(Boolean) contextA.getProperty("freeFlight").value) {
                        weldJointDef = new WeldJointDef();
                        weldJointDef.initialize(bodyA, bodyB, bodyB.getWorldCenter());
                        jointDefs.add(weldJointDef);
                        //bodyA.getWorld().createJoint(weldJointDef);
                    }
                }

                if (nameA == "crate" && nameB == "arrow") {
                    contactPoint = contact.getWorldManifold().getPoints()[0];
                    if (!(Boolean) contextB.getProperty("freeFlight").value && Math.round(contactPoint.x * 10) == 6) {
                        weldJointDef = new WeldJointDef();
                        weldJointDef.initialize(bodyB, bodyA, bodyA.getWorldCenter());
                        jointDefs.add(weldJointDef);
                        //bodyB.getWorld().createJoint(weldJointDef);
                    }
                }

                if (nameB == "crate" && nameA == "arrow") {
                    contactPoint = contact.getWorldManifold().getPoints()[0];
                    if (!(Boolean) contextA.getProperty("freeFlight").value && Math.round(contactPoint.x * 10) == 6) {
                        weldJointDef = new WeldJointDef();
                        weldJointDef.initialize(bodyA, bodyB, bodyB.getWorldCenter());
                        jointDefs.add(weldJointDef);
                        //bodyA.getWorld().createJoint(weldJointDef);
                    }
                }

                if (nameB == "arrow") {
                    contextB.setValueProperty("freeFlight", true);
                }

                if (nameA == "arrow") {
                    contextA.setValueProperty("freeFlight", true);
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
