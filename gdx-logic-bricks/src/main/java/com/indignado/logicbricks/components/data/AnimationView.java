package com.indignado.logicbricks.components.data;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author Rubentxu.
 */
public class AnimationView extends TextureView {
    public IntMap<Animation> animations = new IntMap<Animation>();


    @Override
    public void reset() {
        animations.clear();

    }

}
