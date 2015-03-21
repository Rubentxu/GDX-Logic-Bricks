package com.indignado.functional.test.base;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.config.GameContext;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public abstract class LogicBricksTest implements ApplicationListener {
    protected Game game;
    protected LogicBricksEngine engine;
    protected World physics;
    protected AssetManager assetManager;
    protected LBBuilders builders;
    protected GameContext context;


    @Override
    public void create() {
        Settings.DEBUG = true;
        Settings.DRAW_FPS = true;
        Settings.FIXED_TIME_STEP = 1 / 75F;
        Settings.DRAGGABLE_BOX2D_BODIES = true;
        Settings.DEBUG_LEVEL = Logger.DEBUG;
        Settings.DRAW_BOX2D_ABBs = false;
        Settings.DRAW_BOX2D_BODIES = true;
        Settings.DRAW_BOX2D_JOINTS = true;
        Settings.DRAW_BOX2D_CONTACTS = true;
        Settings.DRAW_BOX2D_VELOCITIES = true;

        //Settings.DEBUG_ENTITY = "Player";
        Settings.DEBUG_TAGS.add("System");
        Settings.DEBUG_TAGS.add("Game");
        Settings.DEBUG_TAGS.add("LogicBricksEngine");
        // Settings.DEBUG_TAGS.add("MotionActuatorSystem");
        //Settings.DEBUG_TAGS.add("EntityBuilder");

        context = new ContextTest();
        context.load();
        engine = context.get(LogicBricksEngine.class);
        physics = context.get(World.class);
        assetManager = context.get(AssetManager.class);
        builders = context.get(LBBuilders.class);
        game = context.get(Game.class);

    }


    public void addLevel(LevelFactory levelFactory) {
        game.addLevelCreator(levelFactory);
        game.createLevel(1);

    }


    @Override
    public void resize(int width, int height) {
        Gdx.app.log("TEST", "Resize : " + width + " " + height);
        Camera camera = context.get(Camera.class);
        camera.viewportHeight = Settings.HEIGHT;
        camera.viewportWidth = Settings.WIDTH;

    }


    @Override
    public void render() {
        Log.debug("TEST", "Update....");
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        game.update(deltaTime);

    }


    @Override
    public void pause() {


    }

    @Override
    public void resume() {


    }


    @Override
    public void dispose() {

    }


}
