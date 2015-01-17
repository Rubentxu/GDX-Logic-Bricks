package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * @author Rubentxu.
 */
public class ParticleEffectView extends View {
    public ParticleEffect effect;
    public boolean autoStart = false;


    @Override
    public void reset() {
        effect = null;
        autoStart = false;

    }

}
