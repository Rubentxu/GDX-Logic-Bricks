package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class MovementComponent extends Component {
    public Vector2 velocity = new Vector2();
    public Vector2 force = new Vector2();
    public Vector2 impulse = new Vector2();

    public float angularVelocity;
    public float torque;
    public float angularImpulse;

    public boolean fixedRotation;

}
