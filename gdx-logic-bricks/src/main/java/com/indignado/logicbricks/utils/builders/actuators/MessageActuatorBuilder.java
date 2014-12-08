package com.indignado.logicbricks.utils.builders.actuators;

import com.indignado.logicbricks.core.actuators.MessageActuator;

/**
 * @author Rubentxu.
 */
public class MessageActuatorBuilder extends ActuatorBuilder<MessageActuator> {

    public MessageActuatorBuilder() {
        brick = new MessageActuator();

    }

    public MessageActuatorBuilder setMessage(String message) {
        brick.message = message;
        return this;

    }


    public MessageActuatorBuilder setDelay(float delay) {
        brick.delay = delay;
        return this;

    }


    public MessageActuatorBuilder setExtraInfo(Object extraInfo) {
        brick.extraInfo = extraInfo;
        return this;

    }


    @Override
    public MessageActuator getBrick() {
        MessageActuator brickTemp = brick;
        brick = new MessageActuator();
        return brickTemp;

    }

}
