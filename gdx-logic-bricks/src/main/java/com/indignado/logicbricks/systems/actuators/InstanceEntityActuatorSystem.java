package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.gdx.physics.box2d.Body;

import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.InstanceEntityActuatorComponent;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;

/**
 * @author Rubentxu
 */
public class InstanceEntityActuatorSystem extends ActuatorSystem<InstanceEntityActuator, InstanceEntityActuatorComponent> {
    private World world;


    public InstanceEntityActuatorSystem(World world) {
        super(InstanceEntityActuatorComponent.class);
        this.world = world;

    }


    @Override
    public void processActuator(InstanceEntityActuator actuator) {
        if (evaluateController(actuator)) {
            if (actuator.type == InstanceEntityActuator.Type.AddEntity) {
                Entity entity = actuator.entityFactory.createEntity();
                Body body = actuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();
                world.positioningEntity(entity,body.getPosition().x,body.getPosition().y,0);
                world.getEngine().addEntity(entity);
            }
        }

    }

}
