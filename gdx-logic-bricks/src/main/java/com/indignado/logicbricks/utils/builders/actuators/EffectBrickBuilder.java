package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.data.ParticleEffectView;
import com.indignado.logicbricks.core.actuators.EffectActuator;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class EffectBrickBuilder extends ActuatorBuilder<EffectActuator> {


    public EffectBrickBuilder setEffectView(ParticleEffectView effectView) {
        brick.effectView = effectView;
        return this;

    }


    public EffectBrickBuilder setPosition(Vector2 position) {
        brick.position = position;
        return this;

    }


    public EffectBrickBuilder setRotation(float rotation) {
        brick.rotation = rotation;
        return this;

    }


    public EffectBrickBuilder setOpacity(int opacity) {
        brick.opacity = opacity;
        return this;

    }


    public EffectBrickBuilder setTint(Color tint) {
        brick.tint = tint;
        return this;

    }


    public EffectBrickBuilder setActive(boolean active) {
        brick.active = active;
        return this;

    }

}
