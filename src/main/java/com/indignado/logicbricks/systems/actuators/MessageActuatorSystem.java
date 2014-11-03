package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.systems.LogicBricksSystem;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class MessageActuatorSystem extends LogicBricksSystem {

    public MessageActuatorSystem() {
        super();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        for (MessageActuator actuator : getActuators(MessageActuator.class, entity)) {
            MessageDispatcher.getInstance().dispatchMessage(actuator, actuator.message, actuator.extraInfo);

        }

    }


}
