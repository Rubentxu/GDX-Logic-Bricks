package com.indignado.logicbricks.systems.controllers;

import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.core.LogicBrick;
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

        if (controller.actuators.size == 0 || controller.sensors.size == 0) {
            return;
        }

        boolean doUpdate = false;
        boolean seed = true, last = false, pos = false;

        switch (controller.op) {
            case OP_NOR:
            case OP_OR: {
                Iterator<ObjectMap.Entry<String, Sensor>> it = controller.sensors.iterator();
                while (it.hasNext()) {
                    Sensor sens = it.next().value;

                    pos = sens.positive;
                    if (pos)
                        doUpdate = true;
                    if (controller.op == ConditionalController.Op.OP_NOR)
                        controller.isInverter = true;

                    if (doUpdate)
                        break;
                }

                if (controller.isInverter)
                    doUpdate = !doUpdate;
            }
            break;
            case OP_XNOR:
            case OP_XOR: {
                Iterator<ObjectMap.Entry<String, Sensor>> it = controller.sensors.iterator();
                while (it.hasNext()) {

                    Sensor sens = it.next().value;
                    seed = sens.positive;

                    if (seed && last) {
                        doUpdate = false;
                        break;
                    } else if (seed) doUpdate = true;

                    if (!last && seed)
                        last = true;

                    if (controller.op == ConditionalController.Op.OP_XNOR && seed)
                        controller.isInverter = true;
                }
                if (controller.isInverter)
                    doUpdate = !doUpdate;
            }
            break;
            case OP_NAND:
            case OP_AND: {
                Iterator<ObjectMap.Entry<String, Sensor>> it = controller.sensors.iterator();
                while (it.hasNext()) {

                    Sensor sens = it.next().value;

                    pos = sens.positive;
                    if (seed) {
                        seed = false;
                        doUpdate = pos;
                    } else
                        doUpdate = doUpdate && pos;

                    if (controller.op == ConditionalController.Op.OP_NAND)
                        controller.isInverter = true;

                }
                if (controller.isInverter)
                    doUpdate = !doUpdate;
            }
            break;
        }
        controller.pulseState = doUpdate ? LogicBrick.BrickMode.BM_ON : LogicBrick.BrickMode.BM_OFF;

    }


}
