package com.indignado.logicbricks.utils.builders.sensors;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.utils.builders.BrickBuilder;

/**
 * @author Rubentxu.
 */
public class MouseSensorBuilder extends SensorBuilder<MouseSensor> {

    public MouseSensorBuilder() {
        brick = new MouseSensor();

    }

    public MouseSensorBuilder setMouseEvent(MouseSensor.MouseEvent mouseEvent) {
        brick.mouseEvent = mouseEvent;
        return this;

    }

    public MouseSensorBuilder setTarget(Entity target) {
        brick.target = target;
        return this;

    }


    @Override
    public MouseSensor getBrick() {
        MouseSensor brickTemp = brick;
        brick = new MouseSensor();
        return brickTemp;

    }

}
