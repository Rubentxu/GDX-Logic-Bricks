package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.sensors.PropertySensor;

/**
 * @author Rubentxu.
 */
public class PropertySensorBuilder extends SensorBuilder<PropertySensor> {

    public PropertySensorBuilder() {
        brick = new PropertySensor();

    }

    public PropertySensorBuilder setProperty(String property) {
        brick.property = property;
        return this;

    }


    public <T> PropertySensorBuilder setValue(T value) {
        brick.value = value;
        return this;

    }


    public PropertySensorBuilder setEvaluationType(PropertySensor.EvaluationType evaluationType) {
        brick.evaluationType = evaluationType;
        return this;

    }


    public PropertySensorBuilder setMin(Number min) {
        brick.min = min;
        return this;

    }


    public PropertySensorBuilder setMax(Number max) {
        brick.max = max;
        return this;

    }


    @Override
    public PropertySensor getBrick() {
        PropertySensor brickTemp = brick;
        brick = new PropertySensor();
        return brickTemp;

    }

}
