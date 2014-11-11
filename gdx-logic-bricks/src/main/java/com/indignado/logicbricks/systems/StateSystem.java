package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.components.StateComponent;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class StateSystem extends IteratingSystem {
    private ComponentMapper<StateComponent> sm;


    public StateSystem() {
        super(Family.getFor(StateComponent.class), 0);
        sm = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        sm.get(entity).time += deltaTime;

    }

}



