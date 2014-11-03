package com.indignado.games.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.indignado.games.bricks.controllers.*;
import com.indignado.games.bricks.sensors.KeyboardSensor;
import com.indignado.games.bricks.sensors.MouseSensor;
import com.indignado.games.bricks.sensors.Sensor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
    public void processEntity(Entity entity, float deltaTime) {
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
        while (it.hasNext()){
            Sensor s = it.next();
            if(s.isActive().equals(true)){
                controller.pulseSignal = true;

            }
        }

    }


    public void evaluate(ScriptController controller) {
        Iterator<Script> it = controller.scripts.iterator();
        while (it.hasNext()){
            it.next().execute(controller.sensors);
        }

    }

}
