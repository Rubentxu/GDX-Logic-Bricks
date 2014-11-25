package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;

/**
 * @author Rubentxu.
 */
public class LogicBricksEngine extends Engine {
    private ObjectMap<Long, Entity> entitiesIds;


    public LogicBricksEngine() {
        super();
        this.entitiesIds = new ObjectMap<Long, Entity>();

    }


    @Override
    public void addEntity(Entity entity) {
        super.addEntity(entity);
        entitiesIds.put(entity.getId(), entity);
        IdentityComponent identity = getComponent(entity, IdentityComponent.class, true);
        identity.uuid = entity.getId();
        RigidBodiesComponents rigidBodies = getComponent(entity, RigidBodiesComponents.class, false);

        if (rigidBodies != null && identity.filter != null) {
            for (Body rigidBody : rigidBodies.rigidBodies) {
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
