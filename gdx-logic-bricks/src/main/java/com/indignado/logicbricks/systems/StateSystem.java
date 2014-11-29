package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.StateComponent;

/**
 * @author Rubentxu
 */
public class StateSystem extends IteratingSystem {
    private ComponentMapper<StateComponent> sm;
    private final PooledEngine engine;
    Array<Entity> toRemove;
    private int eraseID = -1;

    public StateSystem(PooledEngine engine) {
        super(Family.all(StateComponent.class).get(), 0);
        this.engine = engine;
        sm = ComponentMapper.getFor(StateComponent.class);
        toRemove = new Array<Entity>();

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity : toRemove) {
            engine.removeEntity(entity);
        }

        toRemove.clear();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        StateComponent state = sm.get(entity);
        state.time += deltaTime;

        if (state.getCurrentState() == eraseID) {
            toRemove.add(entity);
        }
    }

}



