package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.gdx.ai.msg.Telegram;
import com.indignado.logicbricks.core.sensors.MessageSensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class MessageSensorBuilder extends SensorBuilder<MessageSensor> {

    public MessageSensorBuilder() {
        brick = new MessageSensor();

    }

    public MessageSensorBuilder setMessageListen(String messageListen) {
        brick.messageListen = messageListen;
        return this;

    }


    public MessageSensorBuilder setAutoRegister(boolean autoRegister) {
        brick.autoRegister = autoRegister;
        return this;

    }


    @Override
    public MessageSensor getBrick() {
        MessageSensor brickTemp = brick;
        brick = new MessageSensor();
        return brickTemp;

    }

}
