package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.sensors.PropertySensorComponent;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.data.Property;

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
        Integer state = stateMapper.get(entity).getCurrentState();
        Set<PropertySensor> sensors = sensorMapper.get(entity).sensors.get(state);
        if (sensors != null) {
            for (PropertySensor sensor : sensors) {
                if (isTap(sensor)) sensor.pulseSignal = false;
                else processSensor(sensor, blackBoardMapper.get(entity));

            }
        }

    }


    @Override
    public void processSensor(PropertySensor sensor) {
    }


    public void processSensor(PropertySensor sensor, BlackBoardComponent blackBoardComponent) {
        boolean isActive = false;
        Property property = blackBoardComponent.getProperty(sensor.property);
        switch (sensor.evaluationType) {
            case CHANGED:
                if (!sensor.value.equals(property.value)) {
                    sensor.setValue(property.value);
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
