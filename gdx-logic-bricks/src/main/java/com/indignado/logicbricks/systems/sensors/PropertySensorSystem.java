package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.sensors.PropertySensorComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class PropertySensorSystem extends SensorSystem<PropertySensor, PropertySensorComponent> {
    private final ComponentMapper<BlackBoardComponent> blackBoardMapper;

    public PropertySensorSystem() {
        super(PropertySensorComponent.class, BlackBoardComponent.class);
        this.blackBoardMapper = ComponentMapper.getFor(BlackBoardComponent.class);

    }


    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        Integer state = stateMapper.get(entity).getCurrentState();
        ObjectSet<PropertySensor> sensors = (ObjectSet<PropertySensor>) sensorMapper.get(entity).sensors.get(state);
        if (sensors != null) {
            for (PropertySensor sensor : sensors) {
                boolean doDispatch = false, detDispatch = false;
                if (sensor.oldState != sensor.state) {
                    sensor.firstExec = true;
                    sensor.positive = false;
                    sensor.firstTap = Sensor.TapMode.TAP_IN;
                    sensor.oldState = sensor.state;

                }

                boolean doQuery = false;
                if (sensor.firstExec || (++sensor.tick > sensor.frequency) || sensor.pulse == Sensor.Pulse.PM_IDLE) {
                    doQuery = true;
                    sensor.tick = 0;

                }

                if (doQuery) {
                    // Sensor detection.
                    boolean lp = sensor.positive;
                    sensor.positive = query(sensor, deltaTime, blackBoardMapper.get(entity));

                    // Sensor Pulse.
                    if (sensor.pulse == Sensor.Pulse.PM_IDLE)
                        doDispatch = lp != sensor.positive;
                    else {
                        if (sensor.pulse == Sensor.Pulse.PM_TRUE) {
                            if (!sensor.invert)
                                doDispatch = (lp != sensor.positive) || sensor.positive;
                            else
                                doDispatch = (lp != sensor.positive) || !sensor.positive;
                        }
                        if (sensor.pulse == Sensor.Pulse.PM_FALSE) {
                            if (!sensor.invert)
                                doDispatch = (lp != sensor.positive) || !sensor.positive;
                            else
                                doDispatch = (lp != sensor.positive) || sensor.positive;
                        }
                    }

                    // Tap mode (Switch On->Switch Off)
                    if (sensor.tap && sensor.pulse != Sensor.Pulse.PM_TRUE) {
                        doQuery = sensor.positive;
                        if (sensor.invert)
                            doQuery = !doQuery;

                        doDispatch = false;
                        sensor.pulseState = LogicBrick.BrickMode.BM_OFF;

                        if (sensor.firstTap == Sensor.TapMode.TAP_IN && doQuery) {
                            doDispatch = true;
                            sensor.positive = true;
                            sensor.pulseState = LogicBrick.BrickMode.BM_ON;
                            sensor.firstTap = Sensor.TapMode.TAP_OUT;
                            sensor.lastTap = Sensor.TapMode.TAP_IN;
                        } else if (sensor.lastTap == Sensor.TapMode.TAP_IN) {
                            sensor.positive = false;
                            doDispatch = true;
                            sensor.lastTap = Sensor.TapMode.TAP_OUT;
                        } else {
                            sensor.positive = false;
                            if (!doQuery)
                                sensor.firstTap = Sensor.TapMode.TAP_IN;
                        }
                    } else
                        sensor.pulseState = isPositive(sensor) ? LogicBrick.BrickMode.BM_ON : LogicBrick.BrickMode.BM_OFF;

                    if (sensor.firstExec) {
                        sensor.firstExec = false;
                        if (sensor.invert && !doDispatch)
                            doDispatch = true;
                    }
                    if (!doDispatch)
                        doDispatch = detDispatch;

                    // Dispatch results
                    if (doDispatch)
                        sensor.pulseState = isPositive(sensor) ? LogicBrick.BrickMode.BM_ON : LogicBrick.BrickMode.BM_OFF;
                }
            }

        }
    }

    @Override
    protected boolean query(PropertySensor sensor, float deltaTime) {
        return false;
    }


    public boolean query(PropertySensor sensor, float deltaTime, BlackBoardComponent blackBoardComponent) {
        boolean isActive = false;

        Property property = blackBoardComponent.getProperty(sensor.property);
        switch (sensor.evaluationType) {
            case CHANGED:
                if (!sensor.value.equals(property.value)) {
                    sensor.value = property.value;
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case INTERVAL:
                isActive = intervalEvaluation(sensor.min, sensor.max, (Number) property.value);
                break;
            case NOT_EQUAL:
                if (!sensor.value.equals(property.value)) {
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case EQUAL:
                if (sensor.value.equals(property.value)) {
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case GREATER_THAN:
                isActive = greaterThanEvaluation((Number) sensor.value, (Number) property.value);
                break;
            case LESS_THAN:
                isActive = lessThanEvaluation((Number) sensor.value, (Number) property.value);
                break;
        }
        return isActive;

    }


    private Boolean greaterThanEvaluation(Number p_value, Number p_valueSignal) {
        int result = compare(p_value, p_valueSignal);
        if (result < 0) return true;
        else return false;
    }


    private Boolean lessThanEvaluation(Number p_value, Number p_valueSignal) {
        int result = compare(p_value, p_valueSignal);
        if (result > 0) return true;
        else return false;
    }


    private int compare(Number p_value, Number p_valueSignal) {
        if (p_value instanceof Double) {
            return new Double((Double) p_value).compareTo((Double) p_valueSignal);

        } else if (p_value instanceof Float) {
            return Float.compare((Float) p_value, (Float) p_valueSignal);

        } else if (p_value instanceof Integer) {
            return Integer.compare((Integer) p_value, (Integer) p_valueSignal);

        } else if (p_value instanceof Long) {
            return Long.compare((Long) p_value, (Long) p_valueSignal);

        }
        return 0;
    }


    private Boolean intervalEvaluation(Number p_min, Number p_max, Number p_valueSignal) {
        return compare(p_min, p_valueSignal) <= 0 && compare(p_max, p_valueSignal) >= 0;


    }

}
