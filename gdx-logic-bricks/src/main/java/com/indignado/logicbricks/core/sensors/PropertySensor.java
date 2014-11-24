package com.indignado.logicbricks.core.sensors;

/**
 * @author Rubentxu
 */
public class PropertySensor<T> extends Sensor {
    // Config Values
    public String property;
    public T value;
    public EvaluationType evaluationType;
    public Number min;
    public Number max;


    public PropertySensor setProperty(String property) {
        this.property = property;
        return this;

    }


    public PropertySensor setValue(T value) {
        this.value = value;
        return this;

    }


    public PropertySensor setEvaluationType(EvaluationType evaluationType) {
        this.evaluationType = evaluationType;
        return this;

    }


    public PropertySensor setMin(Number min) {
        this.min = min;
        return this;

    }


    public PropertySensor setMax(Number max) {
        this.max = max;
        return this;

    }


    public enum EvaluationType {CHANGED, INTERVAL, NOT_EQUAL, EQUAL, GREATER_THAN, LESS_THAN}

}
