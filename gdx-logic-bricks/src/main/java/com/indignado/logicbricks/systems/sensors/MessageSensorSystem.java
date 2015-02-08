package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.sensors.MessageSensorComponent;
import com.indignado.logicbricks.core.MessageHandler;
import com.indignado.logicbricks.core.sensors.MessageSensor;

/**
 * @author Rubentxu
 */
public class MessageSensorSystem extends SensorSystem<MessageSensor, MessageSensorComponent> implements EntityListener {

    public MessageSensorSystem() {
        super(MessageSensorComponent.class);

    }


    @Override
    public boolean query(MessageSensor sensor, float deltaTime) {
        boolean isActive = false;
        if (sensor.message != null) {
            if (sensor.managedMessage) sensor.message = null;
            else isActive = true;
        }
        return isActive;

    }


    @Override
    public void entityAdded(Entity entity) {
        MessageSensorComponent messageSensors = entity.getComponent(MessageSensorComponent.class);
        if (messageSensors != null) {
            IntMap.Values<ObjectSet<MessageSensor>> values = messageSensors.sensors.values();
            while (values.hasNext()) {
                for (MessageSensor sensor : values.next()) {
                    if (sensor.autoRegister)
                        MessageManager.getInstance().addListener(sensor, MessageHandler.getMessageKey(sensor.subject));
                }
            }
        }
    }


    @Override
    public void entityRemoved(Entity entity) {
        MessageSensorComponent messageSensors = entity.getComponent(MessageSensorComponent.class);
        if (messageSensors != null) {
            IntMap.Values<ObjectSet<MessageSensor>> values = messageSensors.sensors.values();
            while (values.hasNext()) {
                for (MessageSensor sensor : values.next()) {
                    MessageManager.getInstance().removeListener(sensor, MessageHandler.getMessageKey(sensor.subject));
                }

            }
        }
    }

}
