package com.indignado.logicbricks.bricks.actuators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.data.ParticleEffectView;

/**
 * @author Rubentxu.
 */
public class EffectActuator extends Actuator {
    public ParticleEffectView effectView;
    public Vector2 position;
    public float rotation = 0;
    public int opacity = -1;
    public Color tint;
    public boolean active = false;


    public EffectActuator setEffectView(ParticleEffectView effectView) {
        this.effectView = effectView;
        return this;

    }


    public EffectActuator setPosition(Vector2 position) {
        this.position = position;
        return this;

    }


    public EffectActuator setRotation(float rotation) {
        this.rotation = rotation;
        return this;

    }


    public EffectActuator setOpacity(int opacity) {
        this.opacity = opacity;
        return this;

    }


    public EffectActuator setTint(Color tint) {
        this.tint = tint;
        return this;

    }


    public EffectActuator setActive(boolean active) {
        this.active = active;
        return this;

    }

}
