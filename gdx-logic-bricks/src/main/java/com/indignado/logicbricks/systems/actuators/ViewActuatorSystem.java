package com.indignado.logicbricks.systems.actuators;

import com.indignado.logicbricks.bricks.actuators.TextureViewActuator;
import com.indignado.logicbricks.components.actuators.ViewActuatorComponent;
import com.indignado.logicbricks.data.TextureView;

/**
 * @author Rubentxu
 */
public class ViewActuatorSystem extends ActuatorSystem<TextureViewActuator, ViewActuatorComponent> {


    public ViewActuatorSystem() {
        super(ViewActuatorComponent.class);

    }


    @Override
    public void processActuator(TextureViewActuator actuator) {
        if (evaluateController(actuator)) {
            TextureView view = actuator.targetView;
            if (actuator.height != 0) view.height = actuator.height;
            if (actuator.width != 0) view.width = actuator.width;
            if (actuator.flipX != null) view.flipX = actuator.flipX;
            if (actuator.flipY != null) view.flipY = actuator.flipY;
            if (actuator.opacity != -1) view.opacity = actuator.opacity;
            if (actuator.tint != null) view.tint = actuator.tint;

        }

    }

}
