package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.sensors.MessageSensorComponent;
import com.indignado.logicbricks.core.MessageManager;
import com.indignado.logicbricks.core.sensors.MessageSensor;

/**
 * @author Rubentxu
 */
public class MessageSensorSystem extends SensorSystem<MessageSensor, MessageSensorComponent> implements EntityListener {

    public MessageSensorSystem() {
        super(MessageSensorComponent.class);

    }


    @Override
    public boolean processSensor(MessageSensor sensor, float deltaTime) {
        boolean isActive = false;
        sensor.pulseSignal = isActive;
        return sensor.pulseSignal;

    }


    @Override
    public void entityAdded(Entity entity) {
        MessageSensorComponent messageSensors = entity.getComponent(MessageSensorComponent.class);
        if (messageSensors != null) {
            IntMap<ObjectSet<MessageSensor>> map = messageSensors.sensors;
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
            IntMap<ObjectSet<MessageSensor>> map = messageSensors.sensors;
            for (int i = 0; i < map.size; ++i) {
                for (MessageSensor sensor : map.get(i)) {
                    MessageDispatcher.getInstance().removeListener(sensor, MessageManager.getMessageKey(sensor.messageListen));
                }

            }
        }
    }

}
