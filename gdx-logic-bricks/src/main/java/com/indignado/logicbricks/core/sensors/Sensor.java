package com.indignado.logicbricks.core.sensors;

import com.indignado.logicbricks.core.LogicBrick;

/**
 * @author Rubentxu
 */
public abstract class Sensor extends LogicBrick {
    // pulse
    public boolean pulseSignal = false;
    public float frequency = 0;
    public boolean once = false;

    public float time = 0;
    public boolean initialized = false;

}
