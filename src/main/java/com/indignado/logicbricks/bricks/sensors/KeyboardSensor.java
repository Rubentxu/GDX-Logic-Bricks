package com.indignado.logicbricks.bricks.sensors;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensor extends Sensor {
    // Config Values
    public char key;
    public boolean allKeys = false;
    public boolean logToggle = false;
    public String target = "";

    // Signal Values
    public Set<Character> keysSignal = new HashSet<Character>();


}
