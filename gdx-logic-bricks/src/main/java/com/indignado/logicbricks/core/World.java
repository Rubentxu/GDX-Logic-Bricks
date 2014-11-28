package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.indignado.logicbricks.systems.AnimationSystem;
import com.indignado.logicbricks.systems.RenderingSystem;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.systems.ViewSystem;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.systems.sensors.KeyboardSensorSystem;
import com.indignado.logicbricks.systems.sensors.MouseSensorSystem;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;


/**
 * @author Rubentxu.
 */
public class World implements Disposable {
    private final LogicBricksEngine engine;
    private final AssetManager assetManager;
    private final com.badlogic.gdx.physics.box2d.World physics;
    private final IntMap<LevelFactory> levelFactories;
    private static int levelIndex = 0;
    private final OrthographicCamera camera;


    public World(com.badlogic.gdx.physics.box2d.World physics, AssetManager assetManager,
                 SpriteBatch batch, OrthographicCamera camera) {
        this.physics = physics;
        this.assetManager = assetManager;
        this.camera = camera;
        this.engine = new LogicBricksEngine();
        engine.addSystem(new RenderingSystem(batch, camera, physics));
        engine.addSystem(new ViewSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new KeyboardSensorSystem());
        engine.addEntityListener(engine.getSystem(KeyboardSensorSystem.class));
        engine.addSystem(new MouseSensorSystem());
        engine.addEntityListener(engine.getSystem(MouseSensorSystem.class));
        engine.addSystem(new CollisionSensorSystem());
        engine.addEntityListener(engine.getSystem(CollisionSensorSystem.class));


        InputMultiplexer input = new InputMultiplexer();
        input.addProcessor(engine.getSystem(KeyboardSensorSystem.class));
        input.addProcessor(engine.getSystem(MouseSensorSystem.class));
        Gdx.input.setInputProcessor(input);
        physics.setContactListener(engine.getSystem(CollisionSensorSystem.class));

        LevelFactory.setEntityBuilder(new EntityBuilder(engine));
        LevelFactory.setBodyBuilder(new BodyBuilder(physics));
        this.levelFactories = new IntMap<LevelFactory>();
        engine.update(0);

    }


    public void addLevelCreator(LevelFactory level) {
        levelIndex++;
        levelFactories.put(levelIndex, level);

    }


    public int getLevelsSize() {
        return levelIndex;

    }


    public void createLevel(int levelNumber) {
        engine.removeAllEntities();
        LevelFactory level = levelFactories.get(levelNumber);
        if (level != null) {
            level.loadAssets(assetManager);
            level.createLevel(this);
        }

    }


    public AssetManager getAssetManager() {
        return assetManager;

    }


    public LogicBricksEngine getEngine() {
        return engine;

    }


    public com.badlogic.gdx.physics.box2d.World getPhysics() {
        return physics;

    }


    public OrthographicCamera getCamera() {
        return camera;
    }


    public void update(float deltaTime) {
        engine.update(deltaTime);
        physics.step(deltaTime, 10, 8);

    }


    public void resize(int width, int height) {
        this.camera.viewportHeight = Settings.Height;
        this.camera.viewportWidth = ( Settings.Height / height) * width;

    }


    public void show() {

    }


    public void hide() {

    }


    public void pause() {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(false);
        }
    }


    public void resume() {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(true);
        }
    }


    @Override
    public void dispose() {

    }

}
