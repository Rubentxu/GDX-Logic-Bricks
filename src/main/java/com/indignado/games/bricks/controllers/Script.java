package com.indignado.games.bricks.controllers;

import com.badlogic.gdx.utils.Array;
import com.indignado.games.bricks.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public interface Script {

    public void execute(Array<Sensor> sensors);

}
