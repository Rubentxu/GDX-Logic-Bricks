package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.core.Script;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.MessageSensor;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class CalculateVelocityScript implements Script {
    private boolean init = false;

    @Override
    public void execute(ScriptController controller, ObjectMap<String, Sensor> sensors, ObjectMap<String, Actuator> actuators) {
        MessageSensor messageSensor = (MessageSensor) sensors.get("PositionMessage");

        if (!messageSensor.initialized && messageSensor.pulseSignal) {
            controller.pulseSignal = true;
            MotionActuator motionActuator = (MotionActuator) actuators.get("ActuatorFreeFlight");

            Body ownerBody = motionActuator.targetRigidBody;
            Vector2 mousePosition = (Vector2) messageSensor.message.extraInfo;

            float angle = MathUtils.atan2(mousePosition.y - 240, mousePosition.x - 50);
            ownerBody.setLinearVelocity(new Vector2(30 * MathUtils.cos(angle), 30 * MathUtils.sin(angle)));
            ownerBody.getTransform().setRotation(angle);

        }


    }

}
