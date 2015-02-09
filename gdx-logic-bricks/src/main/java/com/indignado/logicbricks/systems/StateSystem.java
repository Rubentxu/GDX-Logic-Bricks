package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class StateSystem extends LogicBrickSystem {
    Array<Entity> toRemove;
    private ComponentMapper<StateComponent> sm;


    public StateSystem() {
        super(Family.all(StateComponent.class).get(), 0);
        sm = ComponentMapper.getFor(StateComponent.class);
        toRemove = new Array<Entity>();

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity : toRemove) {
            game.getEngine().removeEntity(entity);
            RigidBodiesComponents rigidBodies = entity.getComponent(RigidBodiesComponents.class);
            if (rigidBodies != null) {
                for (Body body : rigidBodies.rigidBodies) {
                    rigidBodies.rigidBodies.removeValue(body, true);
                    game.getPhysics().destroyBody(body);
                    Log.debug(tag, "Remove entity id %d", entity.getId());
                }
            }
        }
        toRemove.clear();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        StateComponent state = sm.get(entity);
        state.time += deltaTime;

        if (state.getCurrentState() == StateComponent.eraseID) {
            Log.debug(tag, "To remove entity id %d", entity.getId());
            toRemove.add(entity);
        }
        if (sm.get(entity).oldState != state.getCurrentState()) {
            sm.get(entity).oldState = state.getCurrentState();
            sm.get(entity).isChanged = true;

        } else {
            sm.get(entity).isChanged = false;
        }

    }

}



