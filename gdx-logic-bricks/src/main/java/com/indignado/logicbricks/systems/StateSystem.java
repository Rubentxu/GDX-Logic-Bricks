package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class StateSystem extends IteratingSystem {
    Array<Entity> toRemove;
    private String tag = this.getClass().getSimpleName();
    private World world;
    private ComponentMapper<StateComponent> sm;


    public StateSystem(World world) {
        super(Family.all(StateComponent.class).get(), 0);
        this.world = world;
        sm = ComponentMapper.getFor(StateComponent.class);
        toRemove = new Array<Entity>();

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity : toRemove) {
            world.getEngine().removeEntity(entity);
            RigidBodiesComponents rigidBodies = entity.getComponent(RigidBodiesComponents.class);
            if (rigidBodies != null) {
                for (Body body : rigidBodies.rigidBodies) {
                    rigidBodies.rigidBodies.removeValue(body, true);
                    world.getPhysics().destroyBody(body);
                    Log.debug(tag, "Remove entity id %d", entity.getId());
                }
            }
        }
        toRemove.clear();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
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



