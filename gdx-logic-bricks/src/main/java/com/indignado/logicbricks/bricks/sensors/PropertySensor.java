package com.indignado.logicbricks.bricks.sensors;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class PropertySensor extends Sensor {
    // Editor Values
    public String propertyName;
    // Config Values
    public EvaluationType evaluationType;
    public Number min;
    public Number max;
    public Object value;
    // Signal Values
    public Object valueSignal;
    public enum EvaluationType {CHANGED, INTERVAL, NOT_EQUAL, EQUAL, GREATER_THAN, LESS_THAN}


}
