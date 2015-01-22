package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.MotionActuatorComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class MotionActuatorSystem extends ActuatorSystem<MotionActuator, MotionActuatorComponent> {


    public MotionActuatorSystem() {
        super(MotionActuatorComponent.class);

    }


    @Override
    public void processActuator(MotionActuator actuator, float deltaTime) {
        if (actuator.targetRigidBody == null) {
            actuator.targetRigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
        }
        Body body = actuator.targetRigidBody;

        if (actuator.pulseState.equals(LogicBrick.BrickMode.BM_ON)) {


            Log.debug(tag, "Actuator: %s", actuator.name);

            if (actuator.velocity != null) {
                Log.debug(tag, "apply velocity: %s", actuator.velocity);
                body.setLinearVelocity(actuator.velocity);
            }
            if (actuator.force != null) {
                Log.debug(tag, "apply force: %s", actuator.force);
                body.applyForce(actuator.force, body.getWorldCenter(), true);
                float angle = MathUtils.atan2(body.getLinearVelocity().y, body.getLinearVelocity().x);
                body.setTransform(body.getPosition().x, body.getPosition().y, angle);
            }

            if (actuator.impulse != null) {
                Log.debug(tag, "Apply impulse: %s", actuator.impulse);
                body.applyLinearImpulse(actuator.impulse, body.getWorldCenter(), true);

            }

            if (!actuator.fixedRotation) {
                if (actuator.angularVelocity != 0) {
                    Log.debug(tag, "Apply angularVelocity: %s", actuator.angularVelocity);
                    body.setAngularVelocity(actuator.angularVelocity);
                } else if (actuator.torque != 0) {
                    Log.debug(tag, "Apply Torque: %s", actuator.torque);
                    body.applyTorque(actuator.torque, true);
                } else if (actuator.angularImpulse != 0) {
                    Log.debug(tag, "Apply angularImpulse: %s", actuator.angularImpulse);
                    body.applyAngularImpulse(actuator.angularImpulse, true);
                }

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

        if (!body.isFixedRotation() && !actuator.fixedRotation && actuator.angularVelocity == 0 && actuator.torque == 0 && actuator.angularImpulse == 0) {
            float angle = MathUtils.atan2(body.getLinearVelocity().y, body.getLinearVelocity().x);
            Log.debug(tag, "velocity %s apply angle: %f", body.getLinearVelocity(), angle);
            body.setTransform(body.getPosition(), angle);
        }

    }


}
