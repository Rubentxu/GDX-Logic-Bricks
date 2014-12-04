package com.indignado.logicbricks.utils.builders.controllers;

import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class ConditionalControllerBuilder extends BrickBuilder<ConditionalController> {


    public ConditionalControllerBuilder() {
        brick = new ConditionalController();

    }

    public ConditionalControllerBuilder setType(ConditionalController.Type type) {
        brick.type = type;
        return this;

    }

    @Override
    public ConditionalController getBrick() {
        ConditionalController brickTemp = brick;
        brick = new ConditionalController();
        return brickTemp;

    }

}
