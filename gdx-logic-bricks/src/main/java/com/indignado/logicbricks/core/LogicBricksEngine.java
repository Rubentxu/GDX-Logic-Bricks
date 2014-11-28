package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.data.RigidBody;

/**
 * @author Rubentxu.
 */
public class LogicBricksEngine extends PooledEngine {
    private ObjectMap<Long, Entity> entitiesIds;


    public LogicBricksEngine() {
        super();
        this.entitiesIds = new ObjectMap<Long, Entity>();

    }


    @Override
    protected void removeEntityInternal(Entity entity) {
        super.removeEntityInternal(entity);
        entitiesIds.remove(entity.getId());

    }


    @Override
    public void addEntity(Entity entity) {
        super.addEntity(entity);
        entitiesIds.put(entity.getId(), entity);

        IdentityComponent identity = getComponent(entity, IdentityComponent.class, true);
        identity.uuid = entity.getId();
        RigidBodiesComponents rigidBodies = getComponent(entity, RigidBodiesComponents.class, false);

        if (rigidBodies != null && identity.filter != null) {
            for (RigidBody rigidBody : rigidBodies.rigidBodies) {
                for (Fixture fixture : rigidBody.body.getFixtureList()) {
                    fixture.setFilterData(identity.filter);
                }
            }
        }

    }


    public <C extends Component> C getComponent(Entity entity, Class<C> clazzComponent, boolean create) {
        C comp = null;
        comp = entity.getComponent(clazzComponent);
        if (comp == null && create) {
            comp = createComponent(clazzComponent);
            entity.add(comp);
        }
        return comp;

    }


    public Entity getEntity(long uuid) {
        return entitiesIds.get(uuid);

    }


}
