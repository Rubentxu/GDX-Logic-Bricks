package com.indignado.logicbricks.utils.builders.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.indignado.logicbricks.core.actuators.CameraActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.utils.builders.actuators.ActuatorBuilder;

/**
 * @author Rubentxu.
 */
public class ConditionalControllerBuilder extends ControllerBuilder<ConditionalController> {


    public ConditionalControllerBuilder setType(ConditionalController.Type type) {
        brick.type = type;
        return this;

    }

}
