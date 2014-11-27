package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.LogicEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.data.RigidBody;

/**
 * @author Rubentxu.
 */
public class LogicBricksEngine extends Engine {
    private final World world;
    private ObjectMap<Long, Entity> entitiesIds;
    private ObjectMap<Class, Array<Entity>> entitiesTypes;
    private final ObjectMap<Class, EntityPool> entityPools;


    public LogicBricksEngine(World world) {
        super();
        this.world = world;
        this.entitiesIds = new ObjectMap<Long, Entity>();
        this.entitiesTypes = new ObjectMap<Class, Array<Entity>>();
        this.entityPools = new ObjectMap<Class, EntityPool>();

    }


    public <T extends LogicEntity> T createEntity (Class<T> clazz) {
        T entity = obtainEntity(clazz);
        this.addEntity(entity);
        return entity;

    }


    public <T extends LogicEntity> T obtainEntity(Class<T> clazz) {
        EntityPool pool = entityPools.get(clazz);
        if (pool == null) {
            pool = new EntityPool(clazz, world,Settings.particlePoolInitialCapacity,Settings.particlePoolMaxCapacity);
            entityPools.put(clazz, pool);
        }
        return (T) pool.obtain();

    }


    public void clearPools() {
        ObjectMap.Values<EntityPool> pools = entityPools.values();
        while (pools.hasNext()) {
            pools.next().clear();
        }

    }


    @Override
    protected void removeEntityInternal (Entity entity) {
        super.removeEntityInternal(entity);
        entitiesIds.remove(entity.getId());
        entitiesTypes.get(entity.getClass()).removeValue(entity,true);
        if (entity instanceof LogicEntity) {
            free((LogicEntity) entity);
        }

    }


    private void free(LogicEntity entity) {
        if (entity == null) throw new IllegalArgumentException("object cannot be null.");
        EntityPool pool = entityPools.get(entity.getClass());
        if (pool == null) return;
        pool.free(entity);

    }




    @Override
    public void addEntity(Entity entity) {
        super.addEntity(entity);
        entitiesIds.put(entity.getId(), entity);
        Array<Entity> typeArray = entitiesTypes.get(entity.getClass());
        if(typeArray == null) {
            typeArray = new Array<>();
            entitiesTypes.put(entity.getClass(),typeArray);
        }
        typeArray.add(entity);
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
        try {
            comp = entity.getComponent(clazzComponent);
            if (comp == null && create) {
                comp = clazzComponent.newInstance();
                entity.add(comp);
            }

        } catch (InstantiationException e) {
            Gdx.app.log("LogicBricksEngine", "Error instance Component " + clazzComponent);
        } catch (IllegalAccessException e) {
            Gdx.app.log("LogicBricksEngine", "Error instance Component " + clazzComponent);
        }
        return comp;

    }


    public Entity getEntity(long uuid) {
        return entitiesIds.get(uuid);

    }


}
