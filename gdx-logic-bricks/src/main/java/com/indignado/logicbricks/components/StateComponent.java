package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * @author Rubentxu
 */
public class StateComponent extends Component implements Poolable {
    public static int eraseID = -1;
    public float time = 0.0f;
    private IntMap<String> states = new IntMap<>();
    private int typeIndex = -1;
    private int currentState = 0;


    public int createState(String state) {
        int ie = ++typeIndex;
        states.put(ie, state);
        return ie;

    }


    public int getCurrentState() {
        return currentState;

    }


    public String getCurrentStateName() {
        return states.get(currentState);

    }


    public String getState(int state) {
        return states.get(state);

    }


    public int getState(String state) {
        return states.findKey(state, false, -1);

    }


    public void changeCurrentState(int newState) {
        if (currentState != newState) {
            currentState = newState;
            time = 0.0f;
        }

    }


    @Override
    public void reset() {
        time = 0.0f;
        states.clear();
        typeIndex = -1;
        currentState = 0;

    }


    public Array<String> getStates() {
        return states.values().toArray();

    }

}



