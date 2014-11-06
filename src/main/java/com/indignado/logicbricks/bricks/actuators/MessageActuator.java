package com.indignado.logicbricks.bricks.actuators;

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

}
