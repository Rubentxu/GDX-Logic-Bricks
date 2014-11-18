package com.indignado.logicbricks.systems.actuators;

import com.badlogic.gdx.physics.box2d.World;
import com.indignado.logicbricks.bricks.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.components.actuators.InstanceEntityActuatorComponent;

/**
 * @author Rubentxu
 */
public class InstanceEntityActuatorSystem extends ActuatorSystem<InstanceEntityActuator, InstanceEntityActuatorComponent> {
    private World physics;


    public InstanceEntityActuatorSystem(World physics) {
        super(InstanceEntityActuatorComponent.class);
        this.physics = physics;

    }


    @Override
    public void processActuator(InstanceEntityActuator actuator) {
        if (evaluateController(actuator)) {

        }

    }


}
