package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.CameraActuatorComponent;

/**
 * @author Rubentxu
 */
public class CameraActuatorSystem extends ActuatorSystem<CameraActuator, CameraActuatorComponent> {


    public CameraActuatorSystem() {
        super(CameraActuatorComponent.class);

    }


    @Override
    public void processActuator(CameraActuator actuator) {
        if (evaluateController(actuator)) {

            RigidBodiesComponents rc = actuator.target.getComponent(RigidBodiesComponents.class);
            Body body = rc.rigidBodies.first();
            Vector2 targetPosition = body.getPosition();
            //Gdx.app.log("CameraActuatorSystem", "Distancia: " + targetPosition.dst2(actuator.camera.position.x, actuator.camera.position.y));
            moveCamera(actuator.camera, rc.rigidBodies.first().getTransform());


        }

    }


    private void moveCamera(OrthographicCamera camera, Transform target) {
        float lerp = 0.08f;
        Vector3 position = camera.position;
        position.x += (target.getPosition().x - position.x) * lerp;
        position.y += (target.getPosition().y - position.y) * lerp;

    }

}
