package com.indignado.logicbricks.systems.actuators;

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
        game.getMessageManager().dispatchMessage(actuator.delay, actuator, actuator.message, actuator.extraInfo);

    }


}
