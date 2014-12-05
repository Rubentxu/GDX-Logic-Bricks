package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.indignado.logicbricks.components.actuators.MessageActuatorComponent;
import com.indignado.logicbricks.core.actuators.MessageActuator;

/**
 * @author Rubentxu
 */
public class MessageActuatorSystem extends ActuatorSystem<MessageActuator, MessageActuatorComponent> {


    public MessageActuatorSystem() {
        super(MessageActuatorComponent.class);


    }


    @Override
    public void processActuator(MessageActuator actuator, float deltaTime) {
        if (evaluateController(actuator))
            MessageDispatcher.getInstance().dispatchMessage(actuator, actuator.message, actuator.extraInfo);

    }


}
