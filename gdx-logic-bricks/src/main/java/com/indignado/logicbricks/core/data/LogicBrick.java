package com.indignado.logicbricks.core.data;

import com.badlogic.ashley.core.Entity;

/**
 * @author Rubentxu.
 */
public class LogicBrick implements Data {
    public String name;
    public int state = -1;
    public Entity owner;
    public BrickMode pulseState = BrickMode.BM_IDLE;


    @Override
    public void reset() {
        name = null;
        state = -1;
        owner = null;
        pulseState = BrickMode.BM_IDLE;

    }


    public enum BrickMode {BM_IDLE, BM_ON, BM_OFF}

}
