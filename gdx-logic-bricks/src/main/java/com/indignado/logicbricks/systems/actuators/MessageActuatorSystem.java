package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.indignado.logicbricks.components.actuators.MessageActuatorComponent;
import com.indignado.logicbricks.core.MessageHandler;
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
        MessageManager.getInstance().dispatchMessage(actuator.delay, actuator, MessageHandler.getMessageKey(actuator.message), actuator.extraInfo);

    }


}
