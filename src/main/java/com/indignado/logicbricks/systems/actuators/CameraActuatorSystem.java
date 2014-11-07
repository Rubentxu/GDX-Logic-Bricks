package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.CameraActuatorComponent;

import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class CameraActuatorSystem extends ActuatorSystem<CameraActuator,CameraActuatorComponent> {


    public CameraActuatorSystem() {
        super(CameraActuatorComponent.class);

    }


    @Override
    public void processActuator(CameraActuator actuator) {
        if (evaluateController(actuator)) {
            RigidBodiesComponents rc = actuator.target.getComponent(RigidBodiesComponents.class);
            Vector2 targetPosition = rc.rigidBodies.first().getPosition();
            if (!(actuator.camera.position.x == targetPosition.x)) {
                actuator.camera.translate(targetPosition);
            }
        }

    }

}
