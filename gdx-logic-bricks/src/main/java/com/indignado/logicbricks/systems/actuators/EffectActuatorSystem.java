package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.indignado.logicbricks.bricks.actuators.EffectActuator;
import com.indignado.logicbricks.components.actuators.EffectActuatorComponent;
import com.indignado.logicbricks.data.ParticleEffectView;

/**
 * @author Rubentxu
 */
public class EffectActuatorSystem extends ActuatorSystem<EffectActuator, EffectActuatorComponent> {


    public EffectActuatorSystem() {
        super(EffectActuatorComponent.class);

    }


    @Override
    public void processActuator(EffectActuator actuator) {
        if (evaluateController(actuator)) {
            ParticleEffectView view = actuator.effectView;
            ParticleEffect effect = view.effect;
            if (actuator.active) effect.reset();
            else effect.allowCompletion();

            if (actuator.opacity != -1) view.setOpacity(actuator.opacity);
            if (actuator.tint != null) view.setTint(actuator.tint);
            if (actuator.position != null) {
                if (view.attachedTransform != null) view.setLocalPosition(actuator.position);
                else view.setPosition(actuator.position);
            }

        }

    }

}
