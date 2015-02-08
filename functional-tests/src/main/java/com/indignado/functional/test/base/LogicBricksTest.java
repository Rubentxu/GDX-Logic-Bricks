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
        Settings.debug = true;
        Settings.drawFPS = true;
        Settings.draggableBodies = true;
        Settings.debugLevel = Logger.DEBUG;
        Settings.drawABBs = false;
        Settings.drawBodies = true;
        Settings.drawJoints = true;
        Settings.drawContacts = true;
        Settings.drawVelocities = true;
        Settings.drawStage = true;
        //Settings.debugEntity = "Player";
        Settings.debugTags.add("System");
        Settings.debugTags.add("Game");
        Settings.debugTags.add("LogicBricksEngine");
        // Settings.debugTags.add("MotionActuatorSystem");
        //Settings.debugTags.add("EntityBuilder");


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
