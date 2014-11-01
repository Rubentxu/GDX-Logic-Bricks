package com.indignado.games.bricks.controllers;

import com.badlogic.gdx.utils.Array;
import com.indignado.games.bricks.sensors.Sensor;
import java.util.Iterator;

/**
 * @author Rubentxu.
 */
public class ScriptController extends Controller{
    public Array<Script> scripts;
    public Array<Sensor> sensors;

    @Override
    public Boolean evaluate() {
        Iterator<Script> it = scripts.iterator();
        while (it.hasNext()){
            it.next().execute(sensors);
        }
        return true;

    }

}
