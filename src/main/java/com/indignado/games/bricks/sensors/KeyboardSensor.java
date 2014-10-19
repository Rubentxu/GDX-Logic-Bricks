package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.Entity;
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
    public char key = Input.Keys.UNKNOWN;
    public boolean allKeys = false;
    public boolean logToggle = false;
    public String target = "";

    // Signal Values
    public Set<Character> keysSignal = new HashSet<Character>();


    public KeyboardSensor(Entity owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;
        boolean isActive = false;

        for (Character ks : keysSignal) {
            if (allKeys) {
                if (!keysSignal.isEmpty()) isActive = true;
                if (logToggle) {
                    target += ks;
                }
            } else {
                if (ks.equals(key)) isActive = true;
            }

        }
        keysSignal.clear();
        return isActive;

    }

}