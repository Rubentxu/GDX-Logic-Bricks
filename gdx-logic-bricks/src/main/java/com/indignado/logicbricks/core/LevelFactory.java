package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.RigidBodiesComponents;

/**
 * @author Rubentxu.
 */
public abstract class LevelFactory {
    protected ObjectMap<Class<? extends EntityFactory>, EntityFactory> entityFactories;


    protected LevelFactory() {
        this.entityFactories = new ObjectMap<Class<? extends EntityFactory>, EntityFactory>();

    }


    public void loadAssets(AssetManager manager) {
        for (EntityFactory factory : entityFactories.values()) {
            factory.loadAssets(manager);
        }
        manager.finishLoading();

    }


    public <T extends EntityFactory> LevelFactory addEntityFactory(T factory) {
        entityFactories.put(factory.getClass(), factory);
        return this;

    }


    public abstract void createLevel(World world);


    public void positioningEntity(Entity entity, float posX, float posY, float angle) {
        RigidBodiesComponents rbc = entity.getComponent(RigidBodiesComponents.class);
        for (Body rigidBody : rbc.rigidBodies) {
            Vector2 originPosition = new Vector2(posX, posY);
            originPosition.add(rigidBody.getPosition());
            rigidBody.setTransform(originPosition, rigidBody.getAngle() + angle);

        }
    }

}
