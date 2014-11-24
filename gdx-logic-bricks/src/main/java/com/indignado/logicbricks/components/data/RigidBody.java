package com.indignado.logicbricks.components.data;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * @author Rubentxu.
 */
public class RigidBody {
    public Body body;
    public Vector2 localPosition = new Vector2();


    public RigidBody setBody(Body body) {
        this.body = body;
        return this;

    }

    public RigidBody setLocalPosition(Vector2 localPosition) {
        this.localPosition = localPosition;
        return this;

    }

}
