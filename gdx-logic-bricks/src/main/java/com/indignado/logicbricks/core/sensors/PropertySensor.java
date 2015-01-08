package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 * @author Rubentxu
 */
public class PropertySensor<T> extends Sensor implements Telegraph {
    // Config Values
    public String property;
    public T value;
    public EvaluationType evaluationType;
    public Number min;
    public Number max;

    // Signal
    public boolean isChanged = false;

    @Override
    public boolean handleMessage(Telegram msg) {
        isChanged = true;
        return true;
    }

    @Override
    public void reset() {
        super.reset();
        property = null;
        value = null;
        evaluationType = null;
        min = null;
        max = null;
        isChanged = false;

    }


    public enum EvaluationType {CHANGED, INTERVAL, NOT_EQUAL, EQUAL, GREATER_THAN, LESS_THAN}

}
