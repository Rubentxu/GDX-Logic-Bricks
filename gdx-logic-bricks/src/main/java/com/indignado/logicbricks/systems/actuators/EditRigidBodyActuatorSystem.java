package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.indignado.logicbricks.bricks.actuators.EditRigidBodyActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.EditRigidBodyActuatorComponent;

/**
 * @author Rubentxu
 */
public class EditRigidBodyActuatorSystem extends ActuatorSystem<EditRigidBodyActuator, EditRigidBodyActuatorComponent> {


    public EditRigidBodyActuatorSystem() {
        super(EditRigidBodyActuatorComponent.class);

    }


    @Override
    public void processActuator(EditRigidBodyActuator actuator) {
        if (evaluateController(actuator)) {
            if (actuator.targetRigidBody == null) {
                actuator.targetRigidBody = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
            }
            actuator.targetRigidBody.setActive(actuator.active);
            actuator.targetRigidBody.setAwake(actuator.awake);
            for (Fixture fixture : actuator.targetRigidBody.getFixtureList()) {
                if (actuator.friction != 0.2f) fixture.setFriction(actuator.friction);
                if (actuator.restitution != 0) fixture.setRestitution(actuator.restitution);
            }
        }

    }


}
