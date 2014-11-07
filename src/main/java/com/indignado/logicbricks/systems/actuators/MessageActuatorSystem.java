package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
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
public class MessageActuatorSystem extends ActuatorSystem {
    private ComponentMapper<StateComponent> stateMapper;
    private ComponentMapper<MessageActuatorComponent> messageActuatorMapper;


    public MessageActuatorSystem() {
        super(Family.getFor(MessageActuatorComponent.class, StateComponent.class));
        messageActuatorMapper = ComponentMapper.getFor(MessageActuatorComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).get();
        Set<MessageActuator> actuators = messageActuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (MessageActuator actuator : actuators) {
                if (evaluateController(actuator))
                    MessageDispatcher.getInstance().dispatchMessage(actuator, actuator.message, actuator.extraInfo);

            }
        }

    }


}
