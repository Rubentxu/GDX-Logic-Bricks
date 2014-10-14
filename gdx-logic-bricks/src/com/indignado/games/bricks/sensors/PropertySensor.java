package com.indignado.games.bricks.sensors;

import com.badlogic.ashley.core.ComponentType;

/**
 * Created on 13/10/14.
 *
 * @author Rubentxu
 */
public class PropertySensor<T> extends Sensor {

    enum EvaluationType {CHANGED, INTERVAL, NOT_EQUAL, EQUAL, GREATER_THAN, LESS_THAN}

    Class<?>[] PropertyType = {Double.class, Integer.class, Float.class,
            Long.class, String.class, Boolean.class};


    // Config Values
    public ComponentType componentType;
    public String propertyName;
    public EvaluationType evaluationType;
    public Class<?> propertyType;
    public Number min;
    public Number max;
    public Object value;

    // Signal Values
    public Object valueSignal;


    public PropertySensor(T owner) {
        super(owner);
    }


    @Override
    public Boolean isActive() {
        if (isTap()) return false;
        if (propertyType.equals(valueSignal.getClass())) return false;

        switch (evaluationType) {
            case CHANGED:
                if (!value.equals(valueSignal)) {
                    value = valueSignal;
                    return true;
                } else {
                    return false;
                }

            case INTERVAL:
                return intervalEvaluation();

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
                return greaterThanEvaluation();
            case LESS_THAN:
                return !greaterThanEvaluation();
        }

        return false;

    }


    private Boolean greaterThanEvaluation() {
        if (propertyType.equals(Double.class)) {
            Double valueTemp = (Double) value;
            Double valueSignalTemp = (Double) valueSignal;

            if (valueSignalTemp > valueTemp) return true;
            else return false;

        } else if (propertyType.equals(Float.class)) {
            Float valueTemp = (Float) value;
            Float valueSignalTemp = (Float) valueSignal;

            if (valueSignalTemp > valueTemp) return true;
            else return false;

        } else if (propertyType.equals(Integer.class)) {
            Integer valueTemp = (Integer) value;
            Integer valueSignalTemp = (Integer) valueSignal;

            if (valueSignalTemp > valueTemp) return true;
            else return false;

        } else if (propertyType.equals(Long.class)) {
            Long valueTemp = (Long) value;
            Long valueSignalTemp = (Long) valueSignal;

            if (valueSignalTemp > valueTemp) return true;
            else return false;

        }
        return false;
    }


    private Boolean intervalEvaluation() {
        if (propertyType.equals(Double.class)) {
            Double minTemp = (Double) min;
            Double maxTemp = (Double) max;
            Double valueSignalTemp = (Double) valueSignal;

            if (minTemp < valueSignalTemp && maxTemp > valueSignalTemp) return true;
            else return false
                    ;
        } else if (propertyType.equals(Float.class)) {
            Float minTemp = (Float) min;
            Float maxTemp = (Float) max;
            Float valueSignalTemp = (Float) valueSignal;

            if (minTemp < valueSignalTemp && maxTemp > valueSignalTemp) return true;
            else return false;

        } else if (propertyType.equals(Integer.class)) {
            Integer minTemp = (Integer) min;
            Integer maxTemp = (Integer) max;
            Integer valueSignalTemp = (Integer) valueSignal;

            if (minTemp < valueSignalTemp && maxTemp > valueSignalTemp) return true;
            else return false;

        } else if (propertyType.equals(Long.class)) {
            Long minTemp = (Long) min;
            Long maxTemp = (Long) max;
            Long valueSignalTemp = (Long) valueSignal;

            if (minTemp < valueSignalTemp && maxTemp > valueSignalTemp) return true;
            else return false;

        }
        return false;
    }

}
