package com.indignado.games.components;

import com.badlogic.ashley.core.Component;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class AnimateStateComponent extends Component {
    private int state = 0;
    public float time = 0.0f;


    public int get() {
        return state;

    }


    public void set(int newState) {
        state = newState;
        time = 0.0f;

    }

}



