package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

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
