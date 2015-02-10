package com.indignado.logicbricks.core.bricks.base;

import com.indignado.logicbricks.systems.sensors.SensorSystem;

/**
 * @author Rubentxu.
 */
public class TestSensorSystem  extends SensorSystem<TestSensor, TestSensorComponent> {
    public static boolean isActive;

    public TestSensorSystem() {
        super(TestSensorComponent.class);
    }


    @Override
    protected boolean query(TestSensor sensor, float deltaTime) {
        return isActive;
    }

}


