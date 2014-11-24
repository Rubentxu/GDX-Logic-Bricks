package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;

/**
 * @author Rubentxu.
 */
public class LogicBricksEngine extends Engine {
    private ObjectMap<Long, Entity> entitiesIds;
    private World physics;
    private EntityBuilder entityBuilder;


    public LogicBricksEngine(World physics) {
        super();
        this.physics = physics;
        this.entitiesIds = new ObjectMap<Long, Entity>();
        this.entityBuilder = new EntityBuilder(this);

    }


    public void addEntity(Entity entity) {
        super.addEntity(entity);
        entitiesIds.put(entity.getId(), entity);

        if (LogicEntity.class.isAssignableFrom(entity.getClass())) {
            ((LogicEntity) entity).createLogicEntity(this, entityBuilder, physics);
            IdentityComponent identity = getComponent(entity, IdentityComponent.class, true);
            identity.uuid = entity.getId();
            RigidBodiesComponents rigidBodies = getComponent(entity, RigidBodiesComponents.class, false);

            if (rigidBodies != null && identity.filter != null) {
                for (Body body : rigidBodies.rigidBodies) {
                    for (Fixture fixture : body.getFixtureList()) {
                        fixture.setFilterData(identity.filter);
                    }
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


    public EntityBuilder getEntityBuilder() {
        return entityBuilder;

    }

}
