package com.indignado.logicbricks.utils.builders.actuators;

import com.badlogic.gdx.math.Vector2;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuatorBuilder extends ActuatorBuilder<InstanceEntityActuator> {

    public InstanceEntityActuatorBuilder() {
        brick = new InstanceEntityActuator();

    }

    public <T extends EntityFactory> InstanceEntityActuatorBuilder setEntityFactory(Class<T> entityFactory) {
        brick.entityFactory = entityFactory;
        return this;

    }


    public InstanceEntityActuatorBuilder setLocalPosition(Vector2 localPosition) {
        brick.localPosition = localPosition;
        return this;

    }


    public InstanceEntityActuatorBuilder setAngle(float angle) {
        brick.angle = angle;
        return this;

    }


    public InstanceEntityActuatorBuilder setInitialVelocity(Vector2 initialVelocity) {
        brick.initialVelocity = initialVelocity;
        return this;

    }


    public InstanceEntityActuatorBuilder setInitialAngularVelocity(float initialAngularVelocity) {
        brick.initialAngularVelocity = initialAngularVelocity;
        return this;

    }


    public InstanceEntityActuatorBuilder setDuration(float duration) {
        brick.duration = duration;
        return this;

    }


    public InstanceEntityActuatorBuilder setType(InstanceEntityActuator.Type type) {
        brick.type = type;
        return this;

    }


    @Override
    public InstanceEntityActuator getBrick() {
        InstanceEntityActuator brickTemp = brick;
        brick = new InstanceEntityActuator();
        return brickTemp;

    }

}
