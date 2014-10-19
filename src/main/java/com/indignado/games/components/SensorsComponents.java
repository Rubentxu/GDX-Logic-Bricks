package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.indignado.games.bricks.sensors.Sensor;

/**
 * Created on 17/10/14.
 *
 * @author Rubentxu
 */
public class SensorsComponents extends Component{
    public Array<Sensor> sensors= new Array<Sensor>();

}
