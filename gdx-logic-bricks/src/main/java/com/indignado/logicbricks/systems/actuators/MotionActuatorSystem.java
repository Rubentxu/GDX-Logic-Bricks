package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.MotionActuatorComponent;
import com.indignado.logicbricks.core.actuators.MotionActuator;

/**
 * @author Rubentxu
 */
public class MotionActuatorSystem extends ActuatorSystem<MotionActuator, MotionActuatorComponent> {


    public MotionActuatorSystem() {
        super(MotionActuatorComponent.class);

    }


    @Override
    public void processActuator(MotionActuator actuator, float deltaTime) {
        if (evaluateController(actuator)) {
            if (actuator.targetRigidBody == null) {
                actuator.targetRigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
            }
            Body body = actuator.targetRigidBody;
            if (actuator.velocity != null) {
                Gdx.app.log("MotionActuatorSystem", "apply velocity: " + actuator.velocity);
                body.setLinearVelocity(actuator.velocity);
            }
            if (actuator.force != null) {
                Gdx.app.log("MotionActuatorSystem", "apply force: " + actuator.force);
                body.applyForce(actuator.force, body.getWorldCenter(), true);
            }

            if (actuator.impulse != null) {
                //Gdx.app.log("MotionActuatorSystem", "apply impulse: " + sensor.impulse);
                body.applyLinearImpulse(actuator.impulse, body.getWorldCenter(), true);

            }

            if (!actuator.fixedRotation) {
                if (actuator.angularVelocity != 0)
                    body.setAngularVelocity(actuator.angularVelocity);
                if (actuator.torque != 0) body.applyTorque(actuator.torque, true);
                if (actuator.angularImpulse != 0)
                    body.applyAngularImpulse(actuator.angularImpulse, true);
            } else {
                if (!actuator.targetRigidBody.isFixedRotation()) body.setFixedRotation(true);
            }
            if (actuator.limitVelocityX > 0) {
                Vector2 vel = body.getLinearVelocity();
                if (Math.abs(vel.x) > actuator.limitVelocityX) {
                    vel.x = Math.signum(vel.x) * actuator.limitVelocityX;
                    body.setLinearVelocity(new Vector2(vel.x, vel.y));
                } else if (Math.abs(vel.y) > actuator.limitVelocityX * 2) {
                    vel.y = Math.signum(vel.y) * actuator.limitVelocityX * 2;
                    body.setLinearVelocity(new Vector2(vel.x, vel.y));
                }

            }
            if (actuator.limitVelocityY != 0 && body.getLinearVelocity().y > actuator.limitVelocityY) {
                Vector2 velocity = body.getLinearVelocity();
                velocity.y = actuator.limitVelocityY;
                body.setLinearVelocity(velocity);
            }
        }

    }


}
