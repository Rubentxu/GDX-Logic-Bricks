package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.sensors.PropertySensorComponent;
import com.indignado.logicbricks.core.MessageManager;
import com.indignado.logicbricks.core.sensors.PropertySensor;

/**
 * @author Rubentxu
 */
public class PropertySensorSystem extends SensorSystem<PropertySensor, PropertySensorComponent> implements EntityListener {
    private final ComponentMapper<BlackBoardComponent> blackBoardMapper;


    public PropertySensorSystem() {
        super(PropertySensorComponent.class, BlackBoardComponent.class);
        this.blackBoardMapper = ComponentMapper.getFor(BlackBoardComponent.class);

    }


    @Override
    public boolean query(PropertySensor sensor, float deltaTime) {
        boolean isActive = false;
        BlackBoardComponent blackBoardComponent = blackBoardMapper.get(sensor.owner);
        Property property = blackBoardComponent.getProperty(sensor.property);
        switch (sensor.evaluationType) {
            case CHANGED:
                if (sensor.isChanged) {
                    sensor.isChanged = false;
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case INTERVAL:
                isActive = intervalEvaluation(sensor.min, sensor.max, (Number) property.getValue());
                break;
            case NOT_EQUAL:
                if (!sensor.value.equals(property.getValue())) {
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case EQUAL:
                if (sensor.value.equals(property.getValue())) {
                    isActive = true;
                } else {
                    isActive = false;
                }
                break;
            case GREATER_THAN:
                isActive = greaterThanEvaluation((Number) sensor.value, (Number) property.getValue());
                break;
            case LESS_THAN:
                isActive = lessThanEvaluation((Number) sensor.value, (Number) property.getValue());
                break;
        }
        return isActive;

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


    @Override
    public void entityAdded(Entity entity) {
        PropertySensorComponent propertySensors = entity.getComponent(PropertySensorComponent.class);
        if (propertySensors != null) {
            IntMap<ObjectSet<PropertySensor>> map = propertySensors.sensors;
            for (int i = 0; i < map.size; ++i) {
                for (PropertySensor sensor : map.get(i)) {
                    if (sensor.evaluationType.equals(PropertySensor.EvaluationType.CHANGED)) {
                        BlackBoardComponent blackBoard = entity.getComponent(BlackBoardComponent.class);
                        if (blackBoard != null) {
                            if (blackBoard.hasProperty(sensor.property)) {
                                Property property = blackBoard.getProperty(sensor.property);
                                property.setObservable(true);
                                MessageDispatcher.getInstance().addListener(sensor, MessageManager.getMessageKey(sensor.property + "_Changed"));
                            }
                        }
                    }
                }

            }
        }

    }


    @Override
    public void entityRemoved(Entity entity) {
        PropertySensorComponent propertySensors = entity.getComponent(PropertySensorComponent.class);
        if (propertySensors != null) {
            IntMap<ObjectSet<PropertySensor>> map = propertySensors.sensors;
            for (int i = 0; i < map.size; ++i) {
                for (PropertySensor sensor : map.get(i)) {
                    if (sensor.evaluationType.equals(PropertySensor.EvaluationType.CHANGED))
                        MessageDispatcher.getInstance().removeListener(sensor, MessageManager.getMessageKey(sensor.property + "_Changed"));
                }

            }
        }
    }

}
