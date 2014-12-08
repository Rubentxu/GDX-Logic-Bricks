package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.core.Script;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.core.actuators.MessageActuator;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.MessageSensor;
import com.indignado.logicbricks.core.sensors.MouseSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public class MousePositionScript implements Script {
    private boolean init = false;

    @Override
    public void execute(ScriptController controller, ObjectMap<String, Sensor> sensors, ObjectMap<String, Actuator> actuators) {
        MouseSensor mouseSensor = (MouseSensor) sensors.get("SensorMouse");

        if (mouseSensor.pulseSignal) {
            controller.pulseSignal = true;
            Vector2 mousePosition = new Vector2(mouseSensor.positionXsignal,mouseSensor.positionYsignal);
            Log.debug("MousePositionScript::Trigger","mouse position %s",mousePosition);
            InstanceEntityActuator instanceEntityActuator = (InstanceEntityActuator) actuators.get("ActuatorInstanceDart");
            Body ownerBody = instanceEntityActuator.owner.getComponent(RigidBodiesComponents.class).rigidBodies.first();


            float angle = MathUtils.atan2(mousePosition.y - ownerBody.getPosition().y, mousePosition.x - ownerBody.getPosition().x) ;
            instanceEntityActuator.initialVelocity = new Vector2(Settings.Width * MathUtils.cos(angle), Settings.Width  * MathUtils.sin(angle));
            instanceEntityActuator.angle = angle;

            Log.debug("MousePositionScript::Trigger","Initial Velocity %s Angle %f",instanceEntityActuator.initialVelocity.toString(),angle);

        }

    }

}
