package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.LogicBrick.BrickMode;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.core.sensors.Sensor.Pulse;
import com.indignado.logicbricks.core.sensors.Sensor.TapMode;
import com.indignado.logicbricks.systems.LogicBrickSystem;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public abstract class SensorSystem<S extends Sensor, SC extends SensorComponent> extends LogicBrickSystem {
    protected String tag = this.getClass().getSimpleName();
    protected ComponentMapper<SC> sensorMapper;
    protected ComponentMapper<StateComponent> stateMapper;


    public SensorSystem(Class<SC> clazz) {
        super(Family.all(clazz, StateComponent.class).get(), 1);
        this.sensorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        Log.debug(tag, "Create system family %s", clazz.getSimpleName());
    }


    public SensorSystem(Class<SC> clazz, Class<? extends Component> clazz2) {
        super(Family.all(clazz, clazz2, StateComponent.class).get(), 1);

        this.sensorMapper = ComponentMapper.getFor(clazz);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        Log.debug("Create system family %s and %s", clazz.getSimpleName(), clazz2.getSimpleName());

    }


    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        Integer state = stateMapper.get(entity).getCurrentState();
        ObjectSet<S> sensors = (ObjectSet<S>) sensorMapper.get(entity).sensors.get(state);
        if (sensors != null) {
            for (S sensor : sensors) {
                boolean doDispatch = false, freqDispatch = false;
                if (sensor.oldState != sensor.state) {
                    sensor.firstExec = true;
                    sensor.positive = false;
                    sensor.firstTap = Sensor.TapMode.TAP_IN;
                    sensor.oldState = sensor.state;

                }

                boolean processPulseState = false;
                boolean lastPulse = sensor.positive;
                sensor.positive = query(sensor, deltaTime);

                if (sensor.firstExec || (++sensor.tick > sensor.frequency) || sensor.pulse == Pulse.PM_IDLE.getValue()
                        || (lastPulse != sensor.positive)) {
                    processPulseState = true;
                    if(sensor.tick > sensor.frequency) freqDispatch = true;
                    sensor.tick = 0;

                }

                if (processPulseState) {
                    if (sensor.pulse == Pulse.PM_IDLE.getValue()) {
                        doDispatch = lastPulse != sensor.positive;

                    } else {
                        if (Pulse.isPositivePulseMode(sensor)) {
                            if (!sensor.invert) {
                                doDispatch = (lastPulse != sensor.positive) || sensor.positive;
                            } else {
                                doDispatch = (lastPulse != sensor.positive) || !sensor.positive;
                            }

                        }
                        if (Pulse.isNegativePulseMode(sensor)) {
                            if (!sensor.invert) {
                                doDispatch = (lastPulse != sensor.positive) || !sensor.positive;
                            } else {
                                doDispatch = (lastPulse != sensor.positive) || sensor.positive;
                            }
                        }

                    }

                    // Tap mode (Switch On->Switch Off)
                    if (sensor.tap && !(Pulse.isPositivePulseMode(sensor))) {
                        processPulseState = sensor.positive;
                        if (sensor.invert)
                            processPulseState = !processPulseState;

                        doDispatch = false;
                        sensor.pulseState = BrickMode.BM_OFF;

                        if (sensor.firstTap == TapMode.TAP_IN && processPulseState) {
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
                            if (!processPulseState)
                                sensor.firstTap = TapMode.TAP_IN;
                        }
                    } else sensor.pulseState = isPositive(sensor) ? BrickMode.BM_ON : BrickMode.BM_OFF;

                    if (sensor.firstExec) {
                        sensor.firstExec = false;
                        if (sensor.invert && !doDispatch)
                            doDispatch = true;
                    }


                    // Dispatch results
                    if (doDispatch) {
                        sensor.pulseState = BrickMode.BM_ON;
                    }
                }



                if (!doDispatch && Pulse.isPositivePulseMode(sensor) && sensor.positive && freqDispatch) {
                    sensor.pulseState = BrickMode.BM_ON;
                } else  if (!doDispatch) {
                    sensor.pulseState = BrickMode.BM_OFF;
                }

            }

        }

    }


    protected abstract boolean query(S sensor, float deltaTime);


    protected boolean isPositive(S sensor) {
        boolean result = sensor.positive;
        if (sensor.invert) {
            if (!(sensor.tap && !(Pulse.isPositivePulseMode(sensor))))
                result = !result;
        }
        return result;

    }


}
