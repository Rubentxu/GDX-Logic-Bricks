package com.indignado.games.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class SensorSystem extends IteratingSystem {


    public SensorSystem(Family family, int priority) {
        super(family, priority);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {

    }

}
