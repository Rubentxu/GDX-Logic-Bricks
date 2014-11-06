package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.CameraActuatorComponent;

import java.util.Iterator;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class CameraActuatorSystem extends ActuatorSystem {
    private ComponentMapper<CameraActuatorComponent> cameraActuatorMapper;
    private ComponentMapper<StateComponent> stateMapper;


    public CameraActuatorSystem() {
        super(Family.getFor(CameraActuatorComponent.class, StateComponent.class));
        cameraActuatorMapper = ComponentMapper.getFor(CameraActuatorComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).get();
        Set<CameraActuator> actuators = cameraActuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (CameraActuator actuator : actuators) {
                if(evaluateController(actuator)) {
                    RigidBodiesComponents rc = actuator.target.getComponent(RigidBodiesComponents.class);
                    Vector2 targetPosition = rc.rigidBodies.first().getPosition();
                    if (!(actuator.camera.position.x == targetPosition.x)) {
                        actuator.camera.translate(targetPosition);
                    }
                }

            }
        }

    }

}
