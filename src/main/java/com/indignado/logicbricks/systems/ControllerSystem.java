package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.controllers.*;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.Sensor;

import java.util.Iterator;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class ControllerSystem extends LogicBricksSystem  {

    public ControllerSystem() {
        super();

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) throws LogicBricksException{
        for (AndController controller : getControllers(AndController.class, entity)) {
            evaluate(controller);
        }

        for (OrController controller : getControllers(OrController.class, entity)) {
            evaluate(controller);
        }

        for (ScriptController controller : getControllers(ScriptController.class, entity)) {
            evaluate(controller);
        }

    }


    public void evaluate(AndController controller) {
        Iterator<Sensor> it = controller.sensors.iterator();
        if(!it.hasNext()) throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()){
            Sensor s = it.next();
            if(s.isActive().equals(false)) {
                controller.pulseSignal = false;
                return;

            }
        }

    }


    public void evaluate(OrController controller) {
        Iterator<Sensor> it = controller.sensors.iterator();
        if(!it.hasNext()) throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()){
            Sensor s = it.next();
            if(s.isActive().equals(true)){
                controller.pulseSignal = true;

            }
        }

    }


    public void evaluate(ScriptController controller) {
        Iterator<Script> it = controller.scripts.iterator();
        if(!it.hasNext()) throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()){
            it.next().execute(controller.sensors);
        }

    }

}
