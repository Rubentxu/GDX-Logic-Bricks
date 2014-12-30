package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.indignado.logicbricks.components.actuators.MessageActuatorComponent;
import com.indignado.logicbricks.core.MessageManager;
import com.indignado.logicbricks.core.actuators.MessageActuator;

/**
 * @author Rubentxu
 */
public class MessageActuatorSystem extends ActuatorSystem<MessageActuator, MessageActuatorComponent> {


    public MessageActuatorSystem() {
        super(MessageActuatorComponent.class);


    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        super.processEntity(entity, deltaTime);
        MessageDispatcher.getInstance().update(deltaTime);

    }


    @Override
    public void processActuator(MessageActuator actuator, float deltaTime) {
        MessageDispatcher.getInstance().dispatchMessage(actuator.delay, actuator, MessageManager.getMessageKey(actuator.message), actuator.extraInfo);

    }


}
