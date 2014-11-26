package com.indignado.logicbricks.utils.builders.actuators;

import com.indignado.logicbricks.core.actuators.MessageActuator;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class MessageBrickBuilder extends ActuatorBuilder<MessageActuator> {


    public MessageBrickBuilder setMessage(int message) {
        brick.message = message;
        return this;

    }


    public MessageBrickBuilder setExtraInfo(Object extraInfo) {
        brick.extraInfo = extraInfo;
        return this;

    }


}
