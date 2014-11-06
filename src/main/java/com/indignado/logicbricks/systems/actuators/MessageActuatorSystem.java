package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.MessageActuatorComponent;

import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class MessageActuatorSystem extends IteratingSystem {
    private ComponentMapper<StateComponent> stateMapper;
    private ComponentMapper<MessageActuatorComponent> messageActuatorMapper;


    public MessageActuatorSystem() {
        super(Family.getFor(MessageActuatorComponent.class, StateComponent.class), 0);
        messageActuatorMapper = ComponentMapper.getFor(MessageActuatorComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).get();
        Set<MessageActuator> messageActuators = messageActuatorMapper.get(entity).messageActuators.get(state);
        if (messageActuators != null) {
            for (MessageActuator actuator : messageActuators) {
                MessageDispatcher.getInstance().dispatchMessage(actuator, actuator.message, actuator.extraInfo);

            }
        }

    }


}
