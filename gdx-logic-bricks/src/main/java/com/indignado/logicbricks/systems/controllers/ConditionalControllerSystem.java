package com.indignado.logicbricks.systems.controllers;

import com.badlogic.gdx.Gdx;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;

import java.util.Iterator;

/**
 * @author Rubentxu
 */
public class ConditionalControllerSystem extends ControllerSystem<ConditionalController, ConditionalControllerComponent> {


    public ConditionalControllerSystem() {
        super(ConditionalControllerComponent.class);
    }


    @Override
    public void processController(ConditionalController controller) {
        if (controller.type.equals(ConditionalController.Type.AND)) evaluateAndConditional(controller);
        if (controller.type.equals(ConditionalController.Type.OR)) evaluateOrConditional(controller);

    }


    public void evaluateAndConditional(ConditionalController controller) {
        controller.pulseSignal = true;
        Iterator<Sensor> it = controller.sensors.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()) {
            Sensor s = it.next();
            if (s.pulseSignal == false) {
                controller.pulseSignal = false;
                return;
            }
            // s.pulseSignal = false;
        }
        Gdx.app.log("ConditionalControllerSystem", "controller pulseSignal: "+ controller.pulseSignal);
    }


    public void evaluateOrConditional(ConditionalController controller) {
        controller.pulseSignal = false;
        Iterator<Sensor> it = controller.sensors.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()) {
            Sensor s = it.next();
            if (s.pulseSignal == true) {
                controller.pulseSignal = true;
                s.pulseSignal = false;
            }
        }


    }

}
