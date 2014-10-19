package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.games.bricks.sensors.DelaySensor;
import com.indignado.games.bricks.sensors.Sensor;
import com.indignado.games.components.SensorsComponents;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class SensorSystem extends IteratingSystem {
    private ComponentMapper<SensorsComponents> sm;


    public SensorSystem() {
        super(Family.getFor(SensorsComponents.class), 0);
        sm = ComponentMapper.getFor(SensorsComponents.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        SensorsComponents sensors = sm.get(entity);

        for (Sensor s : sensors.sensors) {
           if(!s.isTap()) {
               if(s instanceof DelaySensor){
                   DelaySensor delaySensor = (DelaySensor) s;
                   delaySensor.deltaTimeSignal = deltaTime;
                   continue;
               }
               if(s instanceof DelaySensor){
                   DelaySensor delaySensor = (DelaySensor) s;
                   delaySensor.deltaTimeSignal = deltaTime;
                   continue;
               }
           }
        }

    }

}
