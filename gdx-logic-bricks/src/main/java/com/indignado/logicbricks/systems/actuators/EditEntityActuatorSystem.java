package com.indignado.logicbricks.systems.actuators;

import com.indignado.logicbricks.bricks.actuators.EditEntityActuator;
import com.indignado.logicbricks.components.actuators.EditEntityActuatorComponent;

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
