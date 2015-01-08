package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.actuators.MotionActuatorComponent;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public class FlyingDartCollisionRule implements ContactListener {
    public Array<WeldJointDef> jointDefs = new Array<>();
    public Array<Joint> destroyJoints = new Array<>();
    private String tag = this.getClass().getSimpleName();

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Vector2 contactPoint;
        WeldJointDef weldJointDef;
        if (contact.isTouching()) {

            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();

            IdentityComponent identityA = ((Entity) bodyA.getUserData()).getComponent(IdentityComponent.class);
            IdentityComponent identityB = ((Entity) bodyB.getUserData()).getComponent(IdentityComponent.class);

            Log.debug(tag, "Contact BodyA %s id %d BodyB %s id %d", identityA.tag, identityA.uuid, identityB.tag, identityB.uuid);

            BlackBoardComponent contextA = ((Entity) bodyA.getUserData()).getComponent(BlackBoardComponent.class);
            BlackBoardComponent contextB = ((Entity) bodyB.getUserData()).getComponent(BlackBoardComponent.class);


            MotionActuatorComponent motionA = ((Entity) bodyA.getUserData()).getComponent(MotionActuatorComponent.class);
            MotionActuatorComponent motionB = ((Entity) bodyB.getUserData()).getComponent(MotionActuatorComponent.class);

            if (identityA.tag == "Dart" && identityB.tag == "Dart") {
                for (JointEdge j : bodyA.getJointList()) {
                    destroyJoints.add(j.joint);

                }
                for (JointEdge j : bodyB.getJointList()) {
                    destroyJoints.add(j.joint);

                }
            }

            if (identityA.tag == "Wall" && identityB.tag == "Dart") {
                if (!(Boolean) contextB.getProperty("freeFlight").getValue()) {
                    weldJointDef = new WeldJointDef();
                    weldJointDef.initialize(bodyB, bodyA, bodyA.getWorldCenter());
                    jointDefs.add(weldJointDef);
                    Log.debug(tag, "Create WeldJoint");


                }
            }

            if (identityB.tag == "Wall" && identityA.tag == "Dart") {
                if (!(Boolean) contextA.getProperty("freeFlight").getValue()) {
                    weldJointDef = new WeldJointDef();
                    weldJointDef.initialize(bodyA, bodyB, bodyB.getWorldCenter());
                    jointDefs.add(weldJointDef);
                    Log.debug(tag, "Create WeldJoint");

                }
            }

            if (identityA.tag == "Crate" && identityB.tag == "Dart") {
                contactPoint = contact.getWorldManifold().getPoints()[0];
                if (!(Boolean) contextB.getProperty("freeFlight").getValue() && Math.round(contactPoint.x * 10) == 6) {
                    weldJointDef = new WeldJointDef();
                    weldJointDef.initialize(bodyB, bodyA, bodyA.getWorldCenter());
                    jointDefs.add(weldJointDef);
                    Log.debug(tag, "Create WeldJoint");
                }
            }

            if (identityB.tag == "Crate" && identityA.tag == "Dart") {
                contactPoint = contact.getWorldManifold().getPoints()[0];
                if (!(Boolean) contextA.getProperty("freeFlight").getValue() && Math.round(contactPoint.x * 10) == 6) {
                    weldJointDef = new WeldJointDef();
                    weldJointDef.initialize(bodyA, bodyB, bodyB.getWorldCenter());
                    jointDefs.add(weldJointDef);
                    Log.debug(tag, "Create WeldJoint");
                }
            }


            if (identityA.tag == "Dart") {
                contextA.setValueProperty("freeFlight", true);
                motionA.actuators.get(0).iterator().next().fixedRotation = true;
            }

            if (identityB.tag == "Dart") {
                contextB.setValueProperty("freeFlight", true);
                motionB.actuators.get(0).iterator().next().fixedRotation = true;

            }

        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
