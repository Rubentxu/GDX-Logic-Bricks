package com.indignado.logicbricks.core.actuators;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 * @author Rubentxu.
 */
public class MessageActuator extends Actuator implements Telegraph {
    public String message;
    public float delay;
    public Object extraInfo;


    @Override
    public boolean handleMessage(Telegram msg) {
        return true;
    }


}
