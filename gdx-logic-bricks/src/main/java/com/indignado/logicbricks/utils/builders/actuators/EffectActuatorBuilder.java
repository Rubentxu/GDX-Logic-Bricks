package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.data.ParticleEffectView;
import com.indignado.logicbricks.core.actuators.EffectActuator;

/**
 * @author Rubentxu.
 */
public class EffectActuatorBuilder extends ActuatorBuilder<EffectActuator> {


    public EffectActuatorBuilder setEffectView(ParticleEffectView effectView) {
        brick.effectView = effectView;
        return this;

    }


    public EffectActuatorBuilder setPosition(Vector2 position) {
        brick.position = position;
        return this;

    }


    public EffectActuatorBuilder setRotation(float rotation) {
        brick.rotation = rotation;
        return this;

    }


    public EffectActuatorBuilder setOpacity(int opacity) {
        brick.opacity = opacity;
        return this;

    }


    public EffectActuatorBuilder setTint(Color tint) {
        brick.tint = tint;
        return this;

    }


    public EffectActuatorBuilder setActive(boolean active) {
        brick.active = active;
        return this;

    }

}
