package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.gdx.ai.msg.Telegram;
import com.indignado.logicbricks.core.sensors.MessageSensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class MessageSensorBuilder extends BrickBuilder<MessageSensor> {


    public MessageSensorBuilder setMessageListen(int messageListen) {
        brick.messageListen = messageListen;
        return this;

    }


    public MessageSensorBuilder setMessage(Telegram message) {
        brick.message = message;
        return this;

    }


}
