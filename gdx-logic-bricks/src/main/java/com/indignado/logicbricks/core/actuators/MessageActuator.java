package com.indignado.logicbricks.core.actuators;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 * @author Rubentxu.
 */
public class MessageActuator extends Actuator implements Telegraph {
    public int message;
    public Object extraInfo;


    @Override
    public boolean handleMessage(Telegram msg) {
        return true;
    }


    public MessageActuator setMessage(int message) {
        this.message = message;
        return this;

    }


    public MessageActuator setExtraInfo(Object extraInfo) {
        this.extraInfo = extraInfo;
        return this;

    }

}