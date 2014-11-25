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
        actuator.effectView = effectView;
        return this;

    }


    public EffectActuatorBuilder setPosition(Vector2 position) {
        actuator.position = position;
        return this;

    }


    public EffectActuatorBuilder setRotation(float rotation) {
        actuator.rotation = rotation;
        return this;

    }


    public EffectActuatorBuilder setOpacity(int opacity) {
        actuator.opacity = opacity;
        return this;

    }


    public EffectActuatorBuilder setTint(Color tint) {
        actuator.tint = tint;
        return this;

    }


    public EffectActuatorBuilder setActive(boolean active) {
        actuator.active = active;
        return this;

    }

}
