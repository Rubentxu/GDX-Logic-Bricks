package com.indignado.logicbricks.bricks.sensors;

import com.badlogic.ashley.core.Entity;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class PropertySensor extends Sensor {

    enum EvaluationType { CHANGED, INTERVAL, NOT_EQUAL, EQUAL, GREATER_THAN, LESS_THAN }

    // Editor Values
    public String propertyName;

    // Config Values
    public EvaluationType evaluationType;
    public Number min;
    public Number max;
    public Object value;

    // Signal Values
    public Object valueSignal;


    public PropertySensor(Entity owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;

        switch (evaluationType) {
            case CHANGED:
                if (!value.equals(valueSignal)) {
                    value = valueSignal;
                    return true;
                } else {
                    return false;
                }

            case INTERVAL:
                return intervalEvaluation(min,max,(Number) valueSignal);

            case NOT_EQUAL:
                if (!value.equals(valueSignal)) {
                    return true;
                } else {
                    return false;
                }

            case EQUAL:
                if (value.equals(valueSignal)) {
                    return true;
                } else {
                    return false;
                }

            case GREATER_THAN:
                return greaterThanEvaluation((Number) value, (Number) valueSignal);

            case LESS_THAN:
                return lessThanEvaluation((Number) value, (Number) valueSignal);

        }

        return false;

    }


    private Boolean greaterThanEvaluation(Number p_value, Number p_valueSignal) {
        int result = compare(p_value, p_valueSignal);
        if( result < 0) return true;
        else return false;
    }


    private Boolean lessThanEvaluation(Number p_value, Number p_valueSignal) {
        int result = compare(p_value, p_valueSignal);
        if( result > 0) return true;
        else return false;
    }


    private int compare( Number p_value, Number p_valueSignal) {
        if (p_value instanceof Double) {
            return new Double((Double)p_value).compareTo((Double) p_valueSignal);

        } else if (p_value instanceof Float) {
            return Float.compare((Float) p_value,(Float) p_valueSignal);

        } else if (p_value instanceof Integer) {
            return Integer.compare((Integer) p_value,(Integer) p_valueSignal);

        } else if (p_value instanceof Long) {
            return Long.compare((Long) p_value,(Long) p_valueSignal);

        }
        return 0;
    }


    private Boolean intervalEvaluation(Number p_min, Number p_max, Number p_valueSignal) {
        return compare(p_min,p_valueSignal) <= 0 &&  compare(p_max,p_valueSignal) >= 0;


    }

}
