package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.components.actuators.MessageActuatorComponent;

/**
 * @author Rubentxu
 */
public class MessageActuatorSystem extends ActuatorSystem<MessageActuator, MessageActuatorComponent> {


    public MessageActuatorSystem() {
        super(MessageActuatorComponent.class);


    }


    @Override
    public void processActuator(MessageActuator actuator) {
        if (evaluateController(actuator))
            MessageDispatcher.getInstance().dispatchMessage(actuator, actuator.message, actuator.extraInfo);

    }


}
