package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class StateComponent extends Component {
    public float time = 0.0f;
    private int currentState;

    public int get() {
        return currentState;

    }


    public void set(int newState) {
        currentState = newState;
        time = 0.0f;

    }

}



