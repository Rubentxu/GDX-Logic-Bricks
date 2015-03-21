package com.indignado.logicbricks.core.bricks.base;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.config.GameContext;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class BaseTest {
    protected String tag = this.getClass().getSimpleName();
    protected final Game game;
    protected LogicBricksEngine engine;
    protected EntityBuilder entityBuilder;
    protected BodyBuilder bodyBuilder;
    protected LBBuilders builders;

    public BaseTest() {
        GdxNativesLoader.load();
        SpriteBatch batch = Mockito.mock(SpriteBatch.class);
        when(batch.getColor()).thenReturn(new Color());

        Gdx.input = Mockito.mock(Input.class);
        Gdx.app = Mockito.mock(Application.class);

        Gdx.graphics = Mockito.mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn((int) Settings.WIDTH);
        when(Gdx.graphics.getHeight()).thenReturn((int) Settings.HEIGHT);

        Settings.DEBUG_LEVEL = Logger.DEBUG;
        Settings.TESTING = true;
        Settings.MAX_STEPS = 60;

        GameContext context = new ContextTest();
        context.load();

        this.game = context.get(Game.class);

        engine = context.get(LogicBricksEngine.class);
        builders = context.get(LBBuilders.class);
        entityBuilder = builders.getEntityBuilder();
        bodyBuilder = builders.getBodyBuilder();

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
