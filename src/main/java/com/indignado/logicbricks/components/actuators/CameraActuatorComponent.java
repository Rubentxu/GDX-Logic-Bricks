package com.indignado.logicbricks.components.actuators;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;

import java.util.Set;

/**
 * Created on 05/11/14.
 *
 * @author Rubentxu
 */
public class CameraActuatorComponent extends Component {
    public IntMap<Set<CameraActuator>> cameraActuators = new IntMap<Set<CameraActuator>>();

}
