package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;

import java.util.List;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class StateComponent extends Component {
    private String currentState;
    public List<String> states;
    public float time = 0.0f;


    public String get() {
        return currentState;

    }


    public void set(String newState) {
        currentState = newState;
        time = 0.0f;

    }

}



