package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.indignado.logicbricks.bricks.actuators.EditEntityActuator;
import com.indignado.logicbricks.bricks.actuators.EditRigidBodyActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.EditEntityActuatorComponent;
import com.indignado.logicbricks.components.actuators.EditRigidBodyActuatorComponent;

/**
 * @author Rubentxu
 */
public class EditEntityActuatorSystem extends ActuatorSystem<EditEntityActuator, EditEntityActuatorComponent> {


    public EditEntityActuatorSystem() {
        super(EditEntityActuatorComponent.class);

    }


    @Override
    public void processActuator(EditEntityActuator actuator) {
        if (evaluateController(actuator)) {

        }

    }


}
