package com.indignado.logicbricks.systems.actuators;

import com.indignado.logicbricks.components.actuators.TextureActuatorComponent;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.actuators.TextureActuator;

/**
 * @author Rubentxu
 */
public class TextureActuatorSystem extends ActuatorSystem<TextureActuator, TextureActuatorComponent> {


    public TextureActuatorSystem() {
        super(TextureActuatorComponent.class);

    }


    @Override
    public void processActuator(TextureActuator actuator, float deltaTime) {
        TextureView view = actuator.textureView;
        if (actuator.height != 0) view.setHeight(actuator.height);
        if (actuator.width != 0) view.setWidth(actuator.width);
        if (actuator.flipX != null) view.setFlipX(actuator.flipX);
        if (actuator.flipY != null) view.setFlipY(actuator.flipY);
        if (actuator.opacity != -1) view.setOpacity(actuator.opacity);
        if (actuator.tint != null) view.setTint(actuator.tint);


    }

}
