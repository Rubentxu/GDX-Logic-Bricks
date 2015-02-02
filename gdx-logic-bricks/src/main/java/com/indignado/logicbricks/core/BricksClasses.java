package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Component;
import com.indignado.logicbricks.systems.LogicBrickSystem;

/**
 * @author Rubentxu.
 */
public class BricksClasses<C extends Component, S extends LogicBrickSystem> {
    public Class<C> component;
    public Class<S> system;

    BricksClasses(Class<C> component, Class<S> system) {
        this.system = system;
        this.component = component;

    }


    public C getComponent(S system) {
        
    }

}

