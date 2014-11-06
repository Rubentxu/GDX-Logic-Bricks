package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;

import java.util.Iterator;

/**
 * @author Rubentxu.
 */
public abstract class ActuatorSystem extends IteratingSystem {

    public ActuatorSystem(Family family) {
        super(family);
    }


    protected boolean evaluateController(Actuator actuator) {
        Iterator<Controller> it = actuator.controllers.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ActuatorSystem", "This actuator does not have any associated controller");
        while (it.hasNext()) {
            if (it.next().pulseSignal == false) return false;

        }
        return true;
    }

}
