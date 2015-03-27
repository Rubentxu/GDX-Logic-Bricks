package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.CameraActuatorComponent;
import com.indignado.logicbricks.core.actuators.CameraActuator;
import com.indignado.logicbricks.core.data.RigidBody2D;

/**
 * @author Rubentxu
 */
public class CameraActuatorSystem extends ActuatorSystem<CameraActuator, CameraActuatorComponent> {


    public CameraActuatorSystem() {
        super(CameraActuatorComponent.class);

    }


    @Override
    public void processActuator(CameraActuator actuator, float deltaTime) {
        Entity target = null;
        if(actuator.followEntity != null)
            target = actuator.followEntity;
        else if(actuator.followTagEntity != null)
            actuator.followEntity = target = engine.getEntities(actuator.followTagEntity).first();

        if(target != null) {
            RigidBodiesComponents rc = target.getComponent(RigidBodiesComponents.class);
            if(rc.rigidBodies.first() instanceof RigidBody2D) {
                Transform transform = ((Body)rc.rigidBodies.first().body).getTransform();
                Vector3 position = actuator.camera.position;
                position.x += (transform.getPosition().x - position.x) * actuator.damping;
                position.y += (transform.getPosition().y - position.y) * actuator.damping;

            }
        }

    }



}
