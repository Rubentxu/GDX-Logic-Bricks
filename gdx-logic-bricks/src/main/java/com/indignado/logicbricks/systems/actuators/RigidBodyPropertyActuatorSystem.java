package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.indignado.logicbricks.bricks.actuators.MotionActuator;
import com.indignado.logicbricks.bricks.actuators.RigidBodyPropertyActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.MotionActuatorComponent;
import com.indignado.logicbricks.components.actuators.RigidBodyPropertyActuatorComponent;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class RigidBodyPropertyActuatorSystem extends ActuatorSystem<RigidBodyPropertyActuator, RigidBodyPropertyActuatorComponent> {


    public RigidBodyPropertyActuatorSystem() {
        super(RigidBodyPropertyActuatorComponent.class);

    }


    @Override
    public void processActuator(RigidBodyPropertyActuator actuator) {
        if (evaluateController(actuator)) {
            if (actuator.targetRigidBody == null) {
                actuator.targetRigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
            }
            Gdx.app.log("RigidBodyPropertyActuator", "friction: "+ actuator.friction + " restitution: " + actuator.restitution);
            actuator.targetRigidBody.setActive(actuator.active);
            actuator.targetRigidBody.setAwake(actuator.awake);
            for(Fixture fixture: actuator.targetRigidBody.getFixtureList()) {
                if(actuator.friction != 0.2f) fixture.setFriction(actuator.friction);
                if(actuator.restitution != 0) fixture.setRestitution(actuator.restitution);
            }
        }

    }



}