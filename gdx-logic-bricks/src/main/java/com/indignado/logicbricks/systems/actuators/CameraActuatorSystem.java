package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.CameraActuatorComponent;

/**
 * Created on 15/10/14.
 *
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
            Vector2 targetPosition = rc.rigidBodies.first().getPosition();
            Gdx.app.log("CameraActuatorSystem","Distancia: "+targetPosition.dst2(actuator.camera.position.x,actuator.camera.position.y));
            if(targetPosition.dst2(actuator.camera.position.x, actuator.camera.position.y) > actuator.min) actuator.checkMove = true;
            if (targetPosition.equals(new Vector2(actuator.camera.position.x,actuator.camera.position.y))) actuator.checkMove = false;
            if (actuator.checkMove ) {
                moveCamera(actuator.camera,rc.rigidBodies.first().getTransform());
            }

        }

    }


    private void moveCamera (OrthographicCamera camera, Transform target) {
        float lerp = 0.08f;
        Vector3 position = camera.position;
        position.x += ( target.getPosition().x - position.x) * lerp;
        //position.y += ( target.getPosition().y - position.y) * lerp;
    }

}
