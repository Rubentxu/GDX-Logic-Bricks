package com.indignado.games.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * @author Rubentxu.
 */
public class Animation extends com.badlogic.gdx.graphics.g2d.Animation {

    public String state;
    public float time = 0.0f;



    public Animation(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
    }

    public Animation(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
        super(frameDuration, keyFrames, playMode);
    }

    public Animation(float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
    }


    public String get() {
        return state;

    }


    public void set(String newState) {
        state = newState;
        time = 0.0f;

    }


}
