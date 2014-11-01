package com.indignado.games.bricks.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.games.components.RigidBodiesComponents;

/**
 * @author Rubentxu.
 */
public class CameraActuator extends Actuator {
    public OrthographicCamera camera;
    public Entity target;
    public short min;
    public short max;
    public short height;

    // public short damping;


    @Override
    public void execute() {
        RigidBodiesComponents rc = target.getComponent(RigidBodiesComponents.class);
        Vector2 targetPosition = rc.rigidBodies.first().getPosition();
        if(!(camera.position.x == targetPosition.x)){
            camera.translate(targetPosition);
        }


    }
}
