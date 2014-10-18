package com.indignado.games.bricks.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class BaseTest {
    private static final float RUNNING_FRAME_DURATION = 0.02f;
    protected enum PlayerState { WALKING, JUMP, FALL, IDLE }


    protected IntMap<Animation> getAnimations(){
        IntMap<Animation> animations = new IntMap<Animation>();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets/animation/sprites.pack"));

        Array<TextureAtlas.AtlasRegion> heroWalking = atlas.findRegions("Andando");
        Array<TextureAtlas.AtlasRegion> heroJump = atlas.findRegions("Saltando");
        Array<TextureAtlas.AtlasRegion> heroFall = atlas.findRegions("Cayendo");
        Array<TextureAtlas.AtlasRegion> heroIdle = atlas.findRegions("Parado");


        animations.put(0,new Animation(RUNNING_FRAME_DURATION, heroWalking, Animation.PlayMode.LOOP));
        animations.put(1, new Animation(RUNNING_FRAME_DURATION * 7, heroJump, Animation.PlayMode.NORMAL));
        animations.put(2, new Animation(RUNNING_FRAME_DURATION * 5, heroFall, Animation.PlayMode.NORMAL));
        animations.put(3, new Animation(RUNNING_FRAME_DURATION * 4, heroIdle, Animation.PlayMode.LOOP));

        return animations;

    }

}
