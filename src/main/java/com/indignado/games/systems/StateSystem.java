package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.games.components.AnimateStateComponent;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class StateSystem extends IteratingSystem {
    private ComponentMapper<AnimateStateComponent> sm;


    public StateSystem() {
        super(Family.getFor(AnimateStateComponent.class));
        sm = ComponentMapper.getFor(AnimateStateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        sm.get(entity).time += deltaTime;

    }

}



