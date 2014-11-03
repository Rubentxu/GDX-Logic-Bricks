package com.indignado.games.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.games.bricks.sensors.MessageSensor;

/**
 * @author Rubentxu.
 */
public class MessageActuator extends Actuator implements Telegraph{
    public int message;
    public Object extraInfo;


    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }
}
