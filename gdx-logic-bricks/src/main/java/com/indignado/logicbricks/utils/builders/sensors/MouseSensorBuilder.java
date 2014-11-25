package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.sensors.MouseSensor;

/**
 * @author Rubentxu.
 */
public class MouseSensorBuilder extends SensorBuilder<MouseSensor> {


    public MouseSensorBuilder setMouseEvent(MouseSensor.MouseEvent mouseEvent) {
        sensor.mouseEvent = mouseEvent;
        return this;

    }

    public MouseSensorBuilder setTarget(Entity target) {
        sensor.target = target;
        return this;

    }


}
