package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class MessageSensor extends Sensor implements Telegraph {
    // Config Values
    public int messageListen;
    public Telegram message;


    @Override
    public boolean handleMessage(Telegram msg) {
        this.message = msg;
        return pulseSignal = true;

    }

}
