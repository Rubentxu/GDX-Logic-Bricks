package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public abstract class LevelFactory {
    protected String tag = this.getClass().getSimpleName();
    protected ObjectMap<Class<? extends EntityFactory>, EntityFactory> entitiesFactories;
    protected AssetManager assetManager;
    protected LogicBricksEngine engine;


    protected LevelFactory(LogicBricksEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
        entitiesFactories = new ObjectMap<>();

    }


    public void loadAssets() {
        for (EntityFactory factory : entitiesFactories.values()) {
            factory.loadAssets();
        }
        assetManager.finishLoading();

    }


    public <T extends EntityFactory> void addEntityFactory(T factory) {
        entitiesFactories.put(factory.getClass(), factory);

    }


    public void positioningEntity(Entity entity, float posX, float posY, float angle) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        RigidBodiesComponents rbc = entity.getComponent(RigidBodiesComponents.class);
        for (Body rigidBody : rbc.rigidBodies) {
            Vector2 originPosition = new Vector2(posX, posY);
            originPosition.add(rigidBody.getPosition());
            rigidBody.setTransform(originPosition, (rigidBody.getAngle() + angle) * MathUtils.degreesToRadians);
            Log.debug(tag, "Entity initial position %s", originPosition.toString());

        }

    }


    public abstract void createLevel();


}
