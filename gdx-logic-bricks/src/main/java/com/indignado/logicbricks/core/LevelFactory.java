package com.indignado.logicbricks.core;

import box2dLight.Light;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.CameraComponent;
import com.indignado.logicbricks.components.LightComponent;
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


    public void positioningCamera(Entity entity, float posX, float posY) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        CameraComponent cameraComponent = entity.getComponent(CameraComponent.class);
        if(cameraComponent != null && cameraComponent.camera != null) {
            Camera camera = cameraComponent.camera;
            camera.position.set(posX, posY, 0);
            camera.update();
        }
    }


    public void positioningLight(Entity entity, float posX, float posY) {
        if (Settings.DEBUG_ENTITY != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        LightComponent lightComponent = entity.getComponent(LightComponent.class);
        if(lightComponent != null && lightComponent.light != null) {
            Light light = lightComponent.light;
            light.setPosition(posX, posY);

        }
    }


    public abstract void createLevel();


}
