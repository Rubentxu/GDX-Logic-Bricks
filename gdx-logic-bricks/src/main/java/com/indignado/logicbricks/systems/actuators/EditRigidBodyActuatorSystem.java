package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.EditRigidBodyActuatorComponent;
import com.indignado.logicbricks.core.actuators.EditRigidBodyActuator;
import com.indignado.logicbricks.core.data.RigidBody2D;

/**
 * @author Rubentxu
 */
public class EditRigidBodyActuatorSystem extends ActuatorSystem<EditRigidBodyActuator, EditRigidBodyActuatorComponent> {
    RigidBody2D rigidBody2D;

    public EditRigidBodyActuatorSystem() {
        super(EditRigidBodyActuatorComponent.class);

    }


    @Override
    public void processActuator(EditRigidBodyActuator actuator, float deltaTime) {
        if (actuator.targetRigidBody == null) {
            actuator.targetRigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();

        }

        if(actuator.targetRigidBody instanceof RigidBody2D) {
            rigidBody2D = (RigidBody2D) actuator.targetRigidBody;
            rigidBody2D.body.setActive(actuator.active);
            rigidBody2D.body.setAwake(actuator.awake);
            for (Fixture fixture : rigidBody2D.body.getFixtureList()) {
                if (actuator.friction != 0.2f) fixture.setFriction(actuator.friction);
                if (actuator.restitution != 0) fixture.setRestitution(actuator.restitution);
            }
        }


    }


}
