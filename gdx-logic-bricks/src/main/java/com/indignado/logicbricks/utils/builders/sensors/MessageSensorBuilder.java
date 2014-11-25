package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.gdx.ai.msg.Telegram;
import com.indignado.logicbricks.core.sensors.MessageSensor;

/**
 * @author Rubentxu.
 */
public class MessageSensorBuilder extends SensorBuilder<MessageSensor> {


    public MessageSensorBuilder setMessageListen(int messageListen) {
        sensor.messageListen = messageListen;
        return this;

    }


    public MessageSensorBuilder setMessage(Telegram message) {
        sensor.message = message;
        return this;

    }


}
