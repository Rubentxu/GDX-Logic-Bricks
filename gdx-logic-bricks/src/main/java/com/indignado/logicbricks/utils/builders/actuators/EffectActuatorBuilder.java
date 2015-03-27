package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.core.actuators.Effect2DActuator;
import com.indignado.logicbricks.core.data.ParticleEffectView;

/**
 * @author Rubentxu.
 */
public class EffectActuatorBuilder extends ActuatorBuilder<Effect2DActuator> {

    public EffectActuatorBuilder() {
        brick = new Effect2DActuator();
    }

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

    @Override
    public Effect2DActuator getBrick() {
        Effect2DActuator brickTemp = brick;
        brick = new Effect2DActuator();
        return brickTemp;

    }
}
