package com.indignado.logicbricks.systems.controllers;

import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.Sensor;

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
        if (controller.type.equals(ConditionalController.Type.AND)) evaluateANDConditional(controller);
        if (controller.type.equals(ConditionalController.Type.OR)) evaluateORConditional(controller);
        if (controller.type.equals(ConditionalController.Type.NAND)) evaluateNANDConditional(controller);
        if (controller.type.equals(ConditionalController.Type.NOR)) evaluateNORConditional(controller);

    }


    public void evaluateANDConditional(ConditionalController controller) {
        controller.pulseSignal = true;
        Iterator<Sensor> it = controller.sensors.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()) {
            Sensor s = it.next();
            if (s.pulseSignal == false) {
                controller.pulseSignal = false;

            }

        }

    }


    public void evaluateORConditional(ConditionalController controller) {
        controller.pulseSignal = false;
        Iterator<Sensor> it = controller.sensors.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()) {
            Sensor s = it.next();
            if (s.pulseSignal == true) {
                controller.pulseSignal = true;
            }
        }


    }


    public void evaluateNANDConditional(ConditionalController controller) {
        controller.pulseSignal = false;
        Iterator<Sensor> it = controller.sensors.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()) {
            Sensor s = it.next();
            if (s.pulseSignal == false) {
                controller.pulseSignal = true;
            }
        }


    }


    public void evaluateNORConditional(ConditionalController controller) {
        controller.pulseSignal = true;
        Iterator<Sensor> it = controller.sensors.iterator();
        if (!it.hasNext())
            throw new LogicBricksException("ControllerSystem", "This controller does not have any associated sensor");
        while (it.hasNext()) {
            Sensor s = it.next();
            if (s.pulseSignal == true) {
                controller.pulseSignal = false;

            }

        }

    }

}
