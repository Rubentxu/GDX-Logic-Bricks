package com.indignado.logicbricks.bricks.sensors;

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

    public enum EvaluationType {CHANGED, INTERVAL, NOT_EQUAL, EQUAL, GREATER_THAN, LESS_THAN}

}
