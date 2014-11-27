package com.indignado.functional.test.base;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.core.LevelCreator;
import com.indignado.logicbricks.core.Settings;

import java.io.File;
import java.net.URL;

/**
 * @author Rubentxu.
 */
public abstract class LogicBricksTest implements ApplicationListener {
    protected com.indignado.logicbricks.core.World world;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;


    @Override
    public void create() {
        Settings.debug = true;
        Settings.debugLevel = Logger.DEBUG;
        Settings.drawABBs = true;
        Settings.drawBodies = true;
        Settings.drawJoints = true;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        world = new com.indignado.logicbricks.core.World(new World(new Vector2(0, 0.98f), true)
                , new AssetManager(new TestFileHandleResolver()), batch, camera);


    }


    public void addLevel(LevelCreator levelCreator) {
        world.addLevelCreator(levelCreator);
        world.createLevel(1);

    }


    @Override
    public void resize(int width, int height) {
        Gdx.app.log("TEST", "Resize : " + width + " " + height);

    }


    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (deltaTime > 0.1f) deltaTime = 0.1f;
        world.update(deltaTime);


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


    public class TestFileHandleResolver implements FileHandleResolver {
        @Override
        public FileHandle resolve(String fileName) {
            URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
            File file = new File(url.getPath());
            return new FileHandle(file);

        }

    }



}
