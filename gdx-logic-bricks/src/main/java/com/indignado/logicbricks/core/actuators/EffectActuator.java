package com.indignado.logicbricks.core.actuators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.components.data.ParticleEffectView;

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


}
