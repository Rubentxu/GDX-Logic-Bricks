package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.sensors.RaySensorComponent;
import com.indignado.logicbricks.core.sensors.RaySensor;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class RaySensorSystem extends SensorSystem<RaySensor, RaySensorComponent> implements EntityListener {
    private World physics;

    public RaySensorSystem(World physics) {
        super(RaySensorComponent.class);
        this.physics = physics;

    }


    @Override
    public boolean query(RaySensor sensor, float deltaTime) {
        sensor.contacts.clear();
        float angle = sensor.axis2D.ordinal() * 90.0f;

        Vector2 point1 = sensor.attachedRigidBody.getPosition();
        Vector2 point2 = point1.cpy().add(new Vector2((float) MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).scl(sensor.range));

        physics.rayCast(sensor, point1, point2);

        if (sensor.contacts.size > 0) {
            return true;

        }
        return false;

    }


    @Override
    public void entityAdded(Entity entity) {
        Log.debug(tag, "EntityAdded add RaySensors");
        RigidBodiesComponents rigidBodiesComponent = entity.getComponent(RigidBodiesComponents.class);
        RaySensorComponent raySensorComponent = entity.getComponent(RaySensorComponent.class);
        if (raySensorComponent != null) {
            IntMap.Values<ObjectSet<RaySensor>> values = raySensorComponent.sensors.values();
            while (values.hasNext()) {
                for (RaySensor sensor : values.next()) {
                    if (sensor.attachedRigidBody == null)
                        sensor.attachedRigidBody = rigidBodiesComponent.rigidBodies.first();

                }
            }
        }

    }


    @Override
    public void entityRemoved(Entity entity) {

    }

}
