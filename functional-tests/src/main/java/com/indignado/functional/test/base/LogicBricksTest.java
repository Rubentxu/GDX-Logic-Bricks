package com.indignado.functional.test.base;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public abstract class LogicBricksTest implements ApplicationListener {
    protected Game game;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;


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


        game = new Game(new AssetManager(new TestFileHandleResolver()), new SpriteBatch());
        batch = game.getBatch();
        camera = game.getCamera();

    }


    public void addLevel(LevelFactory levelFactory) {
        game.addLevelCreator(levelFactory);
        game.createLevel(1);

    }


    @Override
    public void resize(int width, int height) {
        Gdx.app.log("TEST", "Resize : " + width + " " + height);

    }


    @Override
    public void render() {
        Log.debug("TEST", "Update....");
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (deltaTime > 0.1f) deltaTime = 0.1f;
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
