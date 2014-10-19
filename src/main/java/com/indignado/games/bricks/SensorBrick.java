package com.indignado.games.bricks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.indignado.games.bricks.sensors.Sensor;

/**
 * Created on 13/10/14.
 * @author Rubentxu
 */
public class SensorBrick extends LeafTask<Sensor> {


    @Override
    public void run(Sensor sensor) {
        Boolean isActive= sensor.isActive();

        if(isActive) success();
        else fail();
    }


    @Override
    protected Task<Sensor> copyTo(Task<Sensor> task) {
        return task;
    }

}
