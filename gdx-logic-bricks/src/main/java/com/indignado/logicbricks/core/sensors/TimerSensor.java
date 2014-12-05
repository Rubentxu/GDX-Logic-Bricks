package com.indignado.logicbricks.core.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class TimerSensor extends Sensor {
    public static short ALWAYS = 0;
    public boolean repeat = false;
    public short delay = 0;
    public short duration = ALWAYS;

    public float time = 0;

}
