package com.indignado.logicbricks.components.controllers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;

import java.util.Set;

/**
 * Created on 05/11/14.
 *
 * @author Rubentxu
 */
public class ConditionalControllerComponent extends Component {
    public IntMap<Set<ConditionalController>> conditionalControllers = new IntMap<Set<ConditionalController>>();

}
