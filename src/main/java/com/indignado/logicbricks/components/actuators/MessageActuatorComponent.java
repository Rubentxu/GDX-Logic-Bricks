package com.indignado.logicbricks.components.actuators;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;

import java.util.Set;

/**
 * Created on 05/11/14.
 *
 * @author Rubentxu
 */
public class MessageActuatorComponent extends Component {
    public IntMap<Set<MessageActuator>> messageActuators = new IntMap<Set<MessageActuator>>();

}
