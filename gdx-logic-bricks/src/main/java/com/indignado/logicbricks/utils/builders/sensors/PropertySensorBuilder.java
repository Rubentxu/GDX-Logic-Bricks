package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.PropertySensor;

/**
 * @author Rubentxu.
 */
public class PropertySensorBuilder extends SensorBuilder<PropertySensor> {


    public PropertySensorBuilder setProperty(String property) {
        sensor.property = property;
        return this;

    }


    public PropertySensorBuilder setValue(Object value) {
        sensor.value = value;
        return this;

    }


    public PropertySensorBuilder setEvaluationType(PropertySensor.EvaluationType evaluationType) {
        sensor.evaluationType = evaluationType;
        return this;

    }


    public PropertySensorBuilder setMin(Number min) {
        sensor.min = min;
        return this;

    }


    public PropertySensorBuilder setMax(Number max) {
        sensor.max = max;
        return this;

    }


}
