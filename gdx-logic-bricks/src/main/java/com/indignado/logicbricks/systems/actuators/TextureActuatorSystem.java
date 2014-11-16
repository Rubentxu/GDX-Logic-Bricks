package com.indignado.logicbricks.systems.actuators;

import com.indignado.logicbricks.bricks.actuators.TextureActuator;
import com.indignado.logicbricks.components.actuators.TextureActuatorComponent;
import com.indignado.logicbricks.data.TextureView;

/**
 * @author Rubentxu
 */
public class TextureActuatorSystem extends ActuatorSystem<TextureActuator, TextureActuatorComponent> {


    public TextureActuatorSystem() {
        super(TextureActuatorComponent.class);

    }


    @Override
    public void processActuator(TextureActuator actuator) {
        if (evaluateController(actuator)) {
            TextureView view = actuator.textureView;
            if (actuator.height != 0) view.height = actuator.height;
            if (actuator.width != 0) view.width = actuator.width;
            if (actuator.flipX != null) view.flipX = actuator.flipX;
            if (actuator.flipY != null) view.flipY = actuator.flipY;
            if (actuator.opacity != -1) view.opacity = actuator.opacity;
            if (actuator.tint != null) view.tint = actuator.tint;

        }

    }

}
