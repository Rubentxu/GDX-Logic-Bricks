package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensor extends Sensor{

    public char key;
    public boolean allKeys = false;
    public String firstModifier;
    public String secondModifier;
    public boolean logToggle;
    public String target;
    public char keySignal;

    @Override
    public Boolean isActive() {
        initialized= true;
        return true;

    }

}
