package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.data.RigidBody;

/**
 * @author Rubentxu.
 */
public class LogicBricksEngine extends PooledEngine {
    private ObjectMap<Long, Entity> idEntities;
    private ObjectMap<String, Entity> tagEntities;


    public LogicBricksEngine() {
        super();
        this.idEntities = new ObjectMap<Long, Entity>();
        this.tagEntities = new ObjectMap<String, Entity>();

    }


    @Override
    protected void removeEntityInternal(Entity entity) {
        super.removeEntityInternal(entity);
        idEntities.remove(entity.getId());
        IdentityComponent identity = getComponent(entity, IdentityComponent.class, false);
        tagEntities.remove(identity.tag);

    }


    @Override
    public void addEntity(Entity entity) {
        super.addEntity(entity);
        idEntities.put(entity.getId(), entity);
        configEntity(entity);


    }


    private void configEntity(Entity entity) {
        IdentityComponent identity = getComponent(entity, IdentityComponent.class, true);
        identity.uuid = entity.getId();
        tagEntities.put(identity.tag, entity);

        RigidBodiesComponents rigidBodies = getComponent(entity, RigidBodiesComponents.class, false);

        if (rigidBodies != null) {
            for (RigidBody rigidBody : rigidBodies.rigidBodies) {
                for (Fixture fixture : rigidBody.body.getFixtureList()) {
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


}
