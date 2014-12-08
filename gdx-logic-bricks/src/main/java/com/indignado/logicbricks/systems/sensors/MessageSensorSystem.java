package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.components.sensors.MessageSensorComponent;
import com.indignado.logicbricks.core.MessageManager;
import com.indignado.logicbricks.core.sensors.MessageSensor;

import java.util.Set;

/**
 * @author Rubentxu
 */
public class MessageSensorSystem extends SensorSystem<MessageSensor, MessageSensorComponent> implements EntityListener {

    public MessageSensorSystem() {
        super(MessageSensorComponent.class);

    }


    @Override
    public void processSensor(MessageSensor sensor, float deltaTime) {
        boolean isActive = false;

        sensor.pulseSignal = isActive;

    }


    @Override
    public void entityAdded(Entity entity) {
        MessageSensorComponent messageSensors = entity.getComponent(MessageSensorComponent.class);
        if (messageSensors != null) {
            IntMap<Set<MessageSensor>> map = messageSensors.sensors;
            for (int i = 0; i < map.size; ++i) {
                for (MessageSensor sensor : map.get(i)) {
                    if (sensor.autoRegister)
                        MessageDispatcher.getInstance().addListener(sensor, MessageManager.getMessageKey(sensor.messageListen));
                }

            }
        }

    }

    @Override
    public void entityRemoved(Entity entity) {
        MessageSensorComponent messageSensors = entity.getComponent(MessageSensorComponent.class);
        if (messageSensors != null) {
            IntMap<Set<MessageSensor>> map = messageSensors.sensors;
            for (int i = 0; i < map.size; ++i) {
                for (MessageSensor sensor : map.get(i)) {
                    MessageDispatcher.getInstance().removeListener(sensor, MessageManager.getMessageKey(sensor.messageListen));
                }

            }
        }
    }

}
