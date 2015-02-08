package com.indignado.logicbricks.core.bricks.base;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import org.mockito.Mockito;

/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class BaseTest {
    protected final Game game;
    protected LogicBricksEngine engine;
    protected EntityBuilder entityBuilder;
    protected BodyBuilder bodyBuilder;

    public BaseTest() {
        GdxNativesLoader.load();
        Gdx.input = Mockito.mock(Input.class);
        Gdx.app = Mockito.mock(Application.class);

        Settings.debugLevel = Logger.DEBUG;
        Settings.testing = true;
        TestFileHandleResolver fileHandle = new TestFileHandleResolver();
        this.game = new Game(new AssetManager(fileHandle),Mockito.mock(SpriteBatch.class));
        engine = game.getEngine();
        entityBuilder = game.getEntityBuilder();
        bodyBuilder = game.getBodyBuilder();

    }


    protected IntMap<Animation> getAnimations() {
        IntMap<Animation> animations = new IntMap<Animation>();
        Array textureRegion = new Array();
        textureRegion.add(Mockito.mock(TextureRegion.class));
        textureRegion.add(Mockito.mock(TextureRegion.class));
        textureRegion.add(Mockito.mock(TextureRegion.class));

        animations.put(0, new Animation(0.2f, textureRegion, Animation.PlayMode.LOOP));
        animations.put(1, new Animation(0.2f, textureRegion, Animation.PlayMode.NORMAL));

        return animations;

    }



    protected enum PlayerState {WALKING, JUMP, FALL, IDLE}

}
