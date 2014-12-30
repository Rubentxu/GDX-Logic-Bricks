package com.indignado.logicbricks.systems.sensors.base;

import com.indignado.logicbricks.systems.actuators.ActuatorSystem;

/**
 * @author Rubentxu.
 */
public class ActuatorTestSystem extends ActuatorSystem<ActuatorTest, ActuatorTestComponent> {

    public ActuatorTestSystem() {
        super(ActuatorTestComponent.class);

    }


    @Override
    public void processActuator(ActuatorTest actuator, float deltaTime) {

    }

}
