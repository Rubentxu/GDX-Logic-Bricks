package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.core.sensors.TimerSensor;
import com.indignado.logicbricks.utils.Logger;

import java.util.Set;

/**
 * @author Rubentxu.
 */
public abstract class SensorSystem<S extends Sensor, SC extends SensorComponent> extends EntitySystem {
    protected Logger log = new Logger(this.getClass().getSimpleName());
    protected ComponentMapper<SC> sensorMapper;
    protected ComponentMapper<StateComponent> stateMapper;
    private Family family;
    private ImmutableArray<Entity> entities;


    public SensorSystem(Class<SC> clazz) {
        super(1);
        this.family = Family.all(clazz, StateComponent.class).get();
        this.sensorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        log.debug("Create system family %s",clazz.getSimpleName());
    }


    public SensorSystem(Class<SC> clazz, Class<? extends Component> clazz2) {
        super(1);
        this.family = Family.all(clazz, clazz2, StateComponent.class).get();
        this.sensorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        log.debug("Create system family %s and %s",clazz.getSimpleName(),clazz2.getSimpleName());

    }


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(family);
        log.debug("Entities size %d", entities.size());
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
        Integer state = stateMapper.get(entity).getCurrentState();
        Set<S> sensors = (Set<S>) sensorMapper.get(entity).sensors.get(state);
        if (sensors != null) {
            for (S sensor : sensors) {
                sensor.pulseSignal = false;
                log.debug("Sensor init %b once %b", sensor.initialized,sensor.once);
                if (!sensor.initialized && sensor.once) {
                    processSensor(sensor, deltaTime);
                    log.debug("Sensor once Time %f", sensor.time);
                } else if (!sensor.once) {
                    if (sensor.frequency != 0 && !(sensor instanceof TimerSensor)) {
                        if (sensor.time < sensor.frequency) sensor.time += deltaTime;
                        if (sensor.time >= sensor.frequency) {
                            log.debug("Sensor Frequency %f Time %f", sensor.frequency, sensor.time);
                            processSensor(sensor, deltaTime);
                            if (sensor.pulseSignal) sensor.time = 0;
                        }
                    } else {
                        log.debug("Sensor proccess Time %f", sensor.time);
                        processSensor(sensor, deltaTime);
                    }
                }
                if (!sensor.initialized) sensor.initialized = true;
            }
        }

    }


    public abstract void processSensor(S sensor, float deltaTime);


    public ImmutableArray<Entity> getEntities() {
        return entities;
    }


    public Family getFamily() {
        return family;
    }
}
