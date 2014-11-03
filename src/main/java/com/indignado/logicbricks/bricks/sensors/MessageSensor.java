package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;
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


    // Signal Values
    public boolean isActive = false ;


    protected MessageSensor(Entity owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;
        return isActive();

    }


    @Override
    public boolean handleMessage(Telegram msg) {
        return isActive = true;

    }

}
