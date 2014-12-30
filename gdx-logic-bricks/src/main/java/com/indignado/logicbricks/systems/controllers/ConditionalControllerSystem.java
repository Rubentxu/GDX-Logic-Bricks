package com.indignado.logicbricks.systems.controllers;

import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.controllers.ConditionalController.Op;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class ConditionalControllerSystem extends ControllerSystem<ConditionalController, ConditionalControllerComponent> {


    public ConditionalControllerSystem() {
        super(ConditionalControllerComponent.class);
    }


    @Override
    public void processController(ConditionalController controller) {

        if (controller.actuators.size == 0 || controller.sensors.size == 0) {
            return;
        }

        boolean doUpdate = false;
        boolean seed = true, last = false, pos = false;


        switch (controller.op) {
            case OP_NOR:
                controller.isInverter = true;
            case OP_OR: {
                for (Sensor sens : controller.sensors.values()) {
                    pos = sens.positive;
                    if (pos)
                        doUpdate = true;

                    if (doUpdate)
                        break;
                }

                if (controller.isInverter)
                    doUpdate = !doUpdate;
            }
            break;
            case OP_XNOR:
            case OP_XOR: {
                for (Sensor sens : controller.sensors.values()) {
                    seed = sens.positive;

                    if (seed && last) {
                        doUpdate = false;
                        break;
                    } else if (seed) doUpdate = true;

                    if (!last && seed)
                        last = true;

                    if (controller.op == Op.OP_XNOR && seed)
                        controller.isInverter = true;
                }
                if (controller.isInverter)
                    doUpdate = !doUpdate;
            }
            break;
            case OP_NAND:
                controller.isInverter = true;
            case OP_AND: {
                for (Sensor sens : controller.sensors.values()) {
                    pos = sens.positive;
                    if (seed) {
                        seed = false;
                        doUpdate = pos;
                    } else
                        doUpdate = doUpdate && pos;

                }
                if (controller.isInverter) {
                    doUpdate = !doUpdate;
                    Log.debug(tag, "Conditional OP_NAND doUpdate %b", doUpdate);
                } else {
                    Log.debug(tag, "Conditional OP_AND doUpdate %b", doUpdate);
                }


            }
            break;
        }
        if (doUpdate) {
            for (Actuator actuator : controller.actuators.values()) {
                actuator.pulseState = LogicBrick.BrickMode.BM_ON;
                Log.debug(tag, "Actuators %s pulseState %s", actuator.name, actuator.pulseState);
            }
        }

    }


}
