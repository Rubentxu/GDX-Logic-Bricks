package com.indignado.logicbricks.utils.builders.sensors;

import com.indignado.logicbricks.core.data.Axis2D;
import com.indignado.logicbricks.core.sensors.RadarSensor;

/**
 * @author Rubentxu.
 */
public class RadarSensorBuilder extends SensorBuilder<RadarSensor> {

    public RadarSensorBuilder() {
        brick = new RadarSensor();

    }


    public RadarSensorBuilder setTargetTag(String tag) {
        brick.targetTag = tag;
        return this;

    }


    public RadarSensorBuilder setTargetPropertyName(String name) {
        brick.targetPropertyName = name;
        return this;

    }


    public RadarSensorBuilder setAxis(Axis2D axis2D) {
        brick.axis2D = axis2D;
        return this;

    }


    public RadarSensorBuilder setAngle(float angle) {
        brick.angle = angle;
        return this;

    }


    public RadarSensorBuilder setDistance(float distance) {
        brick.distance = distance;
        return this;

    }


    @Override
    public RadarSensor getBrick() {
        RadarSensor brickTemp = brick;
        brick = new RadarSensor();
        return brickTemp;

    }

}
