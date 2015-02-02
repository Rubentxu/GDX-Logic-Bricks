package com.indignado.logicbricks.core.sensors;

import com.indignado.logicbricks.core.data.LogicBrick;

/**
 * @author Rubentxu
 */
public abstract class Sensor extends LogicBrick {

    // config Values
    public float frequency = 0;
    public boolean invert = false;
    public boolean tap = false;
    public int pulse = Pulse.PM_IDLE.value;

    // values
    public float tick = 0;
    public boolean positive = false;
    public boolean firstExec = true;
    public boolean initialized = false;
    public TapMode firstTap = TapMode.TAP_IN;
    public TapMode lastTap = TapMode.TAP_OUT;

    @Override
    public void reset() {
        super.reset();
        frequency = 0;
        invert = false;
        tap = false;
        pulse = Pulse.PM_IDLE.value;
        tick = 0;
        positive = false;
        firstExec = true;
        initialized = false;
        firstTap = TapMode.TAP_IN;
        lastTap = TapMode.TAP_OUT;

    }

    public enum Pulse {
        PM_IDLE,
        PM_TRUE,
        PM_FALSE;

        private int value;


        Pulse() {
            this.value = 1 << ordinal();

        }

        public static boolean isPositivePulseMode(Sensor sensor) {
            return (sensor.pulse & Pulse.PM_TRUE.value) != 0;

        }

        public static boolean isNegativePulseMode(Sensor sensor) {
            return (sensor.pulse & Pulse.PM_FALSE.value) != 0;

        }

        public int getValue() {
            return value;

        }

    }


    public enum TapMode {
        TAP_OUT,
        TAP_IN
    }

}
