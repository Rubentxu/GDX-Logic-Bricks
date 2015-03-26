package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * @author Rubentxu.
 */
public class ParticleEffectView extends View<Transform2D> {
    public ParticleEffect effect;
    public boolean autoStart = false;


    public ParticleEffectView() {
        super.transform = new Transform2D();

    }


    @Override
    public void reset() {
        effect = null;
        autoStart = false;

    }

}
