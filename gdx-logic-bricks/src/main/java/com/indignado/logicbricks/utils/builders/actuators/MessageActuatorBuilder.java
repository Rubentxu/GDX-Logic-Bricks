package com.indignado.logicbricks.utils.builders.actuators;

import com.indignado.logicbricks.core.actuators.MessageActuator;

/**
 * @author Rubentxu.
 */
public class MessageActuatorBuilder extends ActuatorBuilder<MessageActuator> {


    public MessageActuatorBuilder setMessage(int message) {
        actuator.message = message;
        return this;

    }


    public MessageActuatorBuilder setExtraInfo(Object extraInfo) {
        actuator.extraInfo = extraInfo;
        return this;

    }


}
