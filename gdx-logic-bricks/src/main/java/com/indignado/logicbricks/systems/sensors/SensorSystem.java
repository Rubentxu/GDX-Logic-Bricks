package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.core.sensors.Sensor.Pulse;
import com.indignado.logicbricks.core.sensors.Sensor.TapMode;
import com.indignado.logicbricks.utils.Log;

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
                boolean doDispatch = false, detDispatch = false;
                if (sensor.oldState != sensor.state) {
                    sensor.firstExec = true;
                    sensor.positive = false;
                    sensor.firstTap = Sensor.TapMode.TAP_IN;
                    sensor.oldState = sensor.state;

                }

                boolean doQuery = false;
                if (sensor.firstExec || (++sensor.tick > sensor.frequency) || sensor.pulse == Pulse.PM_IDLE) {
                    doQuery = true;
                    sensor.tick = 0;

                }

                if (doQuery) {
                    // Sensor detection.
                    boolean lp = sensor.positive;
                    sensor.positive = query(sensor, deltaTime);

                    // Sensor Pulse.
                    if (sensor.pulse == Pulse.PM_IDLE)
                        doDispatch = lp != sensor.positive;
                    else {
                        if (sensor.pulse == Pulse.PM_TRUE) {
                            if (!sensor.invert)
                                doDispatch = (lp != sensor.positive) || sensor.positive;
                            else
                                doDispatch = (lp != sensor.positive) || !sensor.positive;
                        }
                        if (sensor.pulse == Pulse.PM_FALSE) {
                            if (!sensor.invert)
                                doDispatch = (lp != sensor.positive) || !sensor.positive;
                            else
                                doDispatch = (lp != sensor.positive) || sensor.positive;
                        }
                    }

                    // Tap mode (Switch On->Switch Off)
                    if (sensor.tap && sensor.pulse != Pulse.PM_TRUE) {
                        doQuery = sensor.positive;
                        if (sensor.invert)
                            doQuery = !doQuery;

                        doDispatch = false;
                        sensor.pulseState = BrickMode.BM_OFF;

                        if (sensor.firstTap == TapMode.TAP_IN && doQuery) {
                            doDispatch = true;
                            sensor.positive = true;
                            sensor.pulseState = BrickMode.BM_ON;
                            sensor.firstTap = TapMode.TAP_OUT;
                            sensor.lastTap = TapMode.TAP_IN;
                        } else if (sensor.lastTap == TapMode.TAP_IN) {
                            sensor.positive = false;
                            doDispatch = true;
                            sensor.lastTap = TapMode.TAP_OUT;
                        } else {
                            sensor.positive = false;
                            if (!doQuery)
                                sensor.firstTap = TapMode.TAP_IN;
                        }
                    } else sensor.pulseState = isPositive(sensor) ? BrickMode.BM_ON : BrickMode.BM_OFF;

                    if (sensor.firstExec) {
                        sensor.firstExec = false;
                        if (sensor.invert && !doDispatch)
                            doDispatch = true;
                    }
                    if (!doDispatch)
                        doDispatch = detDispatch;

                    // Dispatch results
                    if (doDispatch) sensor.pulseState =  BrickMode.BM_ON;
                    else sensor.pulseState = BrickMode.BM_OFF;
                }
            }

        }
    }


    protected abstract boolean query(S sensor, float deltaTime);


    public ImmutableArray<Entity> getEntities() {
        return entities;
    }


    public Family getFamily() {
        return family;
    }


    protected boolean isPositive(Sensor sensor) {
        boolean result = sensor.positive;
        if (sensor.invert) {
            if (!(sensor.tap && !(sensor.pulse != Sensor.Pulse.PM_TRUE)))
                result = !result;
        }
        return result;

    }


}
