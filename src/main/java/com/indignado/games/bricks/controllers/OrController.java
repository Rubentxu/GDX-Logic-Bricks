package com.indignado.games.bricks.controllers;

import com.badlogic.gdx.utils.Array;
import com.indignado.games.bricks.sensors.Sensor;

import java.util.Iterator;

/**
 * @author Rubentxu.
 */
public class OrController extends Controller{



    @Override
    public Boolean evaluate(Array<Sensor> sensors) {
       Iterator<Sensor> it = sensors.iterator();
       while (it.hasNext()){
           Sensor s = it.next();
           if(s.isActive().equals(true)) return true;
       }
        return false;

    }

}
