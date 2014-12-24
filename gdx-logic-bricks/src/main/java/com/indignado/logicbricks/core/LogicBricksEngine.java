package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public class LogicBricksEngine extends PooledEngine {
    private String tag = this.getClass().getSimpleName();
    private ObjectMap<Long, Entity> idEntities;
    private ObjectMap<String, Array<Entity>> tagEntities;


    public LogicBricksEngine() {
        super();
        this.idEntities = new ObjectMap<Long, Entity>();
        this.tagEntities = new ObjectMap<String, Array<Entity>>();

    }


    @Override
    protected void removeEntityInternal(Entity entity) {
        IdentityComponent identity = getComponent(entity, IdentityComponent.class, false);
        if (Settings.debugEntity != null) tag = String.format("%s::%s:", this.getClass().getSimpleName(), identity.tag);
        Log.debug(tag, "LogicBricksEngine");
        idEntities.remove(identity.uuid);
        tagEntities.get(identity.tag).removeValue(entity, false);
        super.removeEntityInternal(entity);

    }


    @Override
    public void addEntity(Entity entity) {
        super.addEntity(entity);

        idEntities.put(entity.getId(), entity);
        configEntity(entity);
        Log.debug(tag, "AddEntity %d", entity.getId());

    }


    private void configEntity(Entity entity) {
        IdentityComponent identity = getComponent(entity, IdentityComponent.class, true);
        identity.uuid = entity.getId();
        if (Settings.debugEntity != null) tag = String.format("%s::%s:", this.getClass().getSimpleName(), identity.tag);
        if (!tagEntities.containsKey(identity.tag)) {
            tagEntities.put(identity.tag, new Array<Entity>());

        }
        tagEntities.get(identity.tag).add(entity);

        RigidBodiesComponents rigidBodies = getComponent(entity, RigidBodiesComponents.class, false);

        if (rigidBodies != null) {
            for (Body rigidBody : rigidBodies.rigidBodies) {
                rigidBody.setUserData(entity);
                for (Fixture fixture : rigidBody.getFixtureList()) {
                    Filter filter = new Filter();
                    filter.categoryBits = identity.category;
                    filter.maskBits = identity.collisionMask;
                    filter.groupIndex = identity.group;
                    fixture.setFilterData(filter);
                }
            }
        } else {
            throw new LogicBricksException("LogicBricksEngine", "At least one rigid body is necessary for each entity");
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
        return idEntities.get(uuid);

    }


    public Array<Entity> getEntities(String tag) {
        return tagEntities.get(tag);

    }

}
