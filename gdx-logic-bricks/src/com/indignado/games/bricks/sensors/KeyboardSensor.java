package com.indignado.games.bricks.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class KeyboardSensor<T> extends Sensor {
    // Config Values
    public char key;
    public boolean allKeys = false;
    public boolean logToggle;
    public String target="";

    // Signal Values
    public char keySignal;
    public boolean keyDownSignal;


    public KeyboardSensor(T owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;
        boolean isActive= false;

        if(keyDownSignal){
            if(allKeys ) {
                isActive = true;
                if(logToggle) {
                    target += keySignal;
                }
            } else {
                if(key == keySignal) {
                    isActive = true;
                }
            }

        } else {
            target = "";
        }

        return isActive;

    }

}
