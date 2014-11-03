package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.systems.LogicBricksSystem;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class CameraActuatorSystem extends LogicBricksSystem {

    public CameraActuatorSystem() {
        super();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        for (CameraActuator actuator : getActuators(CameraActuator.class, entity)) {
            execute(actuator);

        }

    }


    public void execute(CameraActuator actuator) {
        RigidBodiesComponents rc = actuator.target.getComponent(RigidBodiesComponents.class);
        Vector2 targetPosition = rc.rigidBodies.first().getPosition();
        if(!(actuator.camera.position.x == targetPosition.x)){
            actuator.camera.translate(targetPosition);
        }

    }

}
