package com.indignado.logicbricks.systems.sensors;

import com.indignado.logicbricks.bricks.sensors.PropertySensor;
import com.indignado.logicbricks.components.sensors.PropertySensorComponent;

/**
 * @author Rubentxu
 */
public class PropertySensorSystem extends SensorSystem<PropertySensor, PropertySensorComponent> {


    public PropertySensorSystem() {
        super(PropertySensorComponent.class);

    }


    @Override
    public void processSensor(PropertySensor sensor) {
        boolean isActive = false;
        switch (sensor.evaluationType) {
            case CHANGED:
                if (!sensor.value.equals(sensor.valueSignal)) {
                    sensor.value = sensor.valueSignal;
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case INTERVAL:
                isActive = intervalEvaluation(sensor.min, sensor.max, (Number) sensor.valueSignal);
                break;
            case NOT_EQUAL:
                if (!sensor.value.equals(sensor.valueSignal)) {
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case EQUAL:
                if (sensor.value.equals(sensor.valueSignal)) {
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case GREATER_THAN:
                isActive = greaterThanEvaluation((Number) sensor.value, (Number) sensor.valueSignal);
                break;
            case LESS_THAN:
                isActive = lessThanEvaluation((Number) sensor.value, (Number) sensor.valueSignal);
                break;
        }
        sensor.pulseSignal = isActive;

    }


    @Override
    public void clearSensor() {

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
