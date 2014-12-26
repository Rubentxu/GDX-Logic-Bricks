package com.indignado.logicbricks.core.sensors;

import com.indignado.logicbricks.core.LogicBrick;

/**
 * @author Rubentxu
 */
public abstract class Sensor extends LogicBrick {

    public enum Pulse {
        PM_IDLE,
        PM_TRUE,
        PM_FALSE
    }


    public enum TapMode {
        TAP_OUT,
        TAP_IN
    }

    // config Values
    public int frequency = 0;
    public boolean invert = false;
    public boolean tap = false;
    public Pulse pulse = Pulse.PM_IDLE;

    // values
    public int tick = 0;
    public boolean positive = false;
    public boolean suspend = false;
    public boolean firstExec = true;
    public float time = 0;
    public boolean initialized = false;
    public int oldState = 0;
    public TapMode firstTap;
    public TapMode lastTap;


    @Override
    public void reset() {
        super.reset();
        tick = 0;
        pulse = Pulse.PM_IDLE;
        invert = false;
        positive = false;
        suspend = false;
        tap = false;
        firstExec = true;
        time = 0;
        initialized = false;
        oldState = 0;
        firstTap = TapMode.TAP_OUT;
        lastTap = TapMode.TAP_OUT;

    }

}
