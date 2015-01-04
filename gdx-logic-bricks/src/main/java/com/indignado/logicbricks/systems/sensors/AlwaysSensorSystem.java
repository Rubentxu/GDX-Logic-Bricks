package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.sensors.AlwaysSensorComponent;
import com.indignado.logicbricks.components.sensors.PropertySensorComponent;
import com.indignado.logicbricks.core.MessageManager;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.PropertySensor;

/**
 * @author Rubentxu
 */
public class AlwaysSensorSystem extends SensorSystem<AlwaysSensor, AlwaysSensorComponent>  {


    public AlwaysSensorSystem() {
        super(AlwaysSensorComponent.class);

    }


    @Override
    public boolean query(AlwaysSensor sensor, float deltaTime) {
        return true;

    }


}
