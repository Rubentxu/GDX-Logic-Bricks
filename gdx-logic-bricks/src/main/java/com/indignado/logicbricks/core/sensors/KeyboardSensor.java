package com.indignado.logicbricks.core.sensors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ObjectSet;

/**
 * @author Rubentxu
 */
public class KeyboardSensor extends Sensor {
    // Config Values
    public int keyCode = Input.Keys.UNKNOWN;
    public boolean allKeys = false;
    public boolean logToggle = false;

    // Signal Values
    public ObjectSet<Character> keysSignal = new ObjectSet<Character>();
    public ObjectSet<Integer> keysCodeSignal = new ObjectSet<Integer>();

    public String target = "";

}
