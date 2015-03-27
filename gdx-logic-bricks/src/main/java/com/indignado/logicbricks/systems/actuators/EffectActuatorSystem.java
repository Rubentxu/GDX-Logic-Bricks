package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.indignado.logicbricks.components.actuators.EffectActuatorComponent;
import com.indignado.logicbricks.core.actuators.Effect2DActuator;
import com.indignado.logicbricks.core.data.ParticleEffectView;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class EffectActuatorSystem extends ActuatorSystem<Effect2DActuator, EffectActuatorComponent> {


    public EffectActuatorSystem() {
        super(EffectActuatorComponent.class);

    }


    @Override
    public void processActuator(Effect2DActuator actuator, float deltaTime) {
        ParticleEffectView view = actuator.effectView;
        ParticleEffect effect = view.effect;
        if (actuator.active) {
            Log.debug(tag, "Effect reset");
            effect.reset();
        } else {
            Log.debug(tag, "Effect allowCompletion");
            effect.allowCompletion();
        }

        if (actuator.opacity != -1) view.setOpacity(actuator.opacity);
        if (actuator.tint != null) view.setTint(actuator.tint);
        if (actuator.position != null) {
            if(view.transform.rigidBody == null) view.transform.matrix.setTranslation(actuator.position.x, actuator.position.y,0);

        }


    }

}
