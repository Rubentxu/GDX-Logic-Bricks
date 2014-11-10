package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public abstract class SensorSystem<S extends Sensor, SC extends SensorComponent> extends EntitySystem {
    private Family family;
    private ImmutableArray<Entity> entities;
    protected ComponentMapper<SC> sensorMapper;
    protected ComponentMapper<StateComponent> stateMapper;


    public SensorSystem(Class<SC> clazz) {
        super(1);
        this.family = Family.all(clazz, StateComponent.class).get();
        this.sensorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(family);
    }


    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }


    @Override
    public void update(float deltaTime) {
        clearSensor();
        for (int i = 0; i < entities.size(); ++i) {
            processEntity(entities.get(i), deltaTime);
        }
    }


    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).get();
        Set<S> sensors = (Set<S>) sensorMapper.get(entity).sensors.get(state);
        if (sensors != null) {
            for (S sensor : sensors) {
                if(sensor instanceof AlwaysSensor) {
                    sensor.pulseSignal= true;
                }
                if(isTap(sensor)) sensor.pulseSignal = false;
                else processSensor(sensor);

            }
        }

    }


    public abstract void processSensor(S sensor);


    public abstract void clearSensor();


    public boolean isTap(Sensor sensor) {
        if (sensor.tap) {
            if (sensor.initialized) {
                return true;
            }
            sensor.initialized = true;
        }
        return false;

    }


    public ImmutableArray<Entity> getEntities() {
        return entities;
    }


}
