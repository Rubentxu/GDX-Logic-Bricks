package com.indignado.logicbricks.core.sensors;

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


}
