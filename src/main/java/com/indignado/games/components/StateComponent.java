package com.indignado.games.components;

import com.badlogic.ashley.core.Component;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class StateComponent extends Component {
    private String state;
    public float time = 0.0f;


    public String get() {
        return state;

    }


    public void set(String newState) {
        state = newState;
        time = 0.0f;

    }

}



