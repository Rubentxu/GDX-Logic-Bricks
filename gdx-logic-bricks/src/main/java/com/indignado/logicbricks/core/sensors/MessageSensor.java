package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class MessageSensor extends Sensor implements Telegraph {
    // Config Values
    public String messageListen;
    public boolean autoRegister = true;


    // Signal
    public Telegram message;


    @Override
    public boolean handleMessage(Telegram msg) {
        this.message = msg;
        return true;

    }

}
