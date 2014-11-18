package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.gdx.Input;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensor extends Sensor {
    // Config Values
    public int keyCode = Input.Keys.UNKNOWN;
    public boolean allKeys = false;
    public boolean logToggle = false;
    public String target = "";

    // Signal Values
    public Set<Character> keysSignal = new HashSet<Character>();
    public Set<Integer> keysCodeSignal = new HashSet<Integer>();


    public KeyboardSensor setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        return this;

    }


    public KeyboardSensor setAllKeys(boolean allKeys) {
        this.allKeys = allKeys;
        return this;

    }


    public KeyboardSensor setLogToggle(boolean logToggle) {
        this.logToggle = logToggle;
        return this;

    }


    public KeyboardSensor setTarget(String target) {
        this.target = target;
        return this;

    }

}
