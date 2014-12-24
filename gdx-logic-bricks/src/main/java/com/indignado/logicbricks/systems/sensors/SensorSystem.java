package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.core.sensors.TimerSensor;
import com.indignado.logicbricks.utils.Log;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public abstract class SensorSystem<S extends Sensor, SC extends SensorComponent> extends EntitySystem {
    protected String tag = this.getClass().getSimpleName();
    protected ComponentMapper<SC> sensorMapper;
    protected ComponentMapper<StateComponent> stateMapper;
    private Family family;
    private ImmutableArray<Entity> entities;


    public SensorSystem(Class<SC> clazz) {
        super(1);
        this.family = Family.all(clazz, StateComponent.class).get();
        this.sensorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        Log.debug(tag, "Create system family %s", clazz.getSimpleName());
    }


    public SensorSystem(Class<SC> clazz, Class<? extends Component> clazz2) {
        super(1);
        this.family = Family.all(clazz, clazz2, StateComponent.class).get();
        this.sensorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        Log.debug("Create system family %s and %s", clazz.getSimpleName(), clazz2.getSimpleName());

    }


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(family);
        Log.debug(tag, "Entities size %d", entities.size());
    }


    @Override
    public void removedFromEngine(Engine engine) {
        entities = null;
    }


    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            processEntity(entities.get(i), deltaTime);
        }
    }


    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        Integer state = stateMapper.get(entity).getCurrentState();
        ObjectSet<S> sensors = (ObjectSet<S>) sensorMapper.get(entity).sensors.get(state);
        if (sensors != null) {
            for (S sensor : sensors) {
                sensor.pulseSignal = false;
                if (!sensor.initialized && sensor.once) {
                    if(!processSensor(sensor, deltaTime)) sensor.initialized = false;
                    Log.debug(tag, "Sensor once Time %f", sensor.time);
                } else if (!sensor.once) {
                    if (sensor.frequency != 0 && !(sensor instanceof TimerSensor)) {
                        if (sensor.time < sensor.frequency) sensor.time += deltaTime;
                        if (sensor.time >= sensor.frequency) {
                            Log.debug(tag, "Sensor Frequency %f Time %f", sensor.frequency, sensor.time);
                            if(!processSensor(sensor, deltaTime)) sensor.initialized = false;
                            if (sensor.pulseSignal) sensor.time = 0;
                        }
                    } else {
                        if(!processSensor(sensor, deltaTime)) sensor.initialized = false;

                    }
                }

                if (!sensor.initialized) sensor.initialized = true;
            }
        }

    }


    public abstract boolean processSensor(S sensor, float deltaTime);


    public ImmutableArray<Entity> getEntities() {
        return entities;
    }


    public Family getFamily() {
        return family;
    }
}
