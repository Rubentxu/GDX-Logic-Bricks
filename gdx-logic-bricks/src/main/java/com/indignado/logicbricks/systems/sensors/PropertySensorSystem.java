package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.sensors.PropertySensorComponent;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.utils.Log;

import java.util.Set;

/**
 * @author Rubentxu
 */
public class PropertySensorSystem extends SensorSystem<PropertySensor, PropertySensorComponent> {
    private final ComponentMapper<BlackBoardComponent> blackBoardMapper;

    public PropertySensorSystem() {
        super(PropertySensorComponent.class, BlackBoardComponent.class);
        this.blackBoardMapper = ComponentMapper.getFor(BlackBoardComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        if (Settings.debugEntity != null) tag = Log.tagEntity(this.getClass().getSimpleName(), entity);
        Integer state = stateMapper.get(entity).getCurrentState();
        ObjectSet<PropertySensor> sensors = sensorMapper.get(entity).sensors.get(state);

        if (sensors != null) {
            for (PropertySensor sensor : sensors) {
                sensor.pulseSignal = false;
                Log.debug(tag, "Sensor init %b once %b", sensor.initialized, sensor.once);
                if (!sensor.initialized && sensor.once) {
                    processSensor(sensor, blackBoardMapper.get(entity));
                    Log.debug(tag, "Sensor once Time %f", sensor.time);
                } else if (!sensor.once) {
                    if (sensor.frequency != 0) {
                        if (sensor.time < sensor.frequency) sensor.time += deltaTime;
                        if (sensor.time >= sensor.frequency) {
                            Log.debug(tag, "Sensor Frequency %f Time %f", sensor.frequency, sensor.time);
                            processSensor(sensor, blackBoardMapper.get(entity));
                            if (sensor.pulseSignal) sensor.time = 0;
                        }
                    } else {
                        processSensor(sensor, blackBoardMapper.get(entity));

                    }
                }
                Log.debug(tag, "Sensor proccess Time %f, name %s pulseSignal %b", sensor.time, sensor.name, sensor.pulseSignal);
                if (!sensor.initialized) sensor.initialized = true;
            }
        }

    }


    @Override
    public boolean processSensor(PropertySensor sensor, float deltaTime) {
        return sensor.pulseSignal;

    }


    public boolean processSensor(PropertySensor sensor, BlackBoardComponent blackBoardComponent) {
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
        sensor.pulseSignal = isActive;
        if (sensor.pulseSignal) Log.debug(tag, "Sensor name %s property %s value %s pulseSignal %b", sensor.name,
                sensor.property, sensor.value, sensor.pulseSignal);
        return sensor.pulseSignal;

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
