package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author Rubentxu.
 */
public class AnimationView extends TextureView {
    public IntMap<Animation> animations;


    @Override
    public void reset() {
        animations.clear();

    }

}
