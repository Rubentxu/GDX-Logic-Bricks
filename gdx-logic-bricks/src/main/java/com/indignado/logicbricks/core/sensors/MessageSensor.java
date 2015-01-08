package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 * @author Rubentxu
 */
public class MessageSensor extends Sensor implements Telegraph {
    // Config Values
    public String subject;
    public boolean autoRegister = true;

    // Signal
    public Telegram message;
    public boolean managedMessage = false;


    @Override
    public boolean handleMessage(Telegram msg) {
        this.message = msg;
        return true;

    }

    @Override
    public void reset() {
        super.reset();
        subject = null;
        autoRegister = true;
        message = null;
        managedMessage = false;

    }
}
