package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.core.Script;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.actuators.MotionActuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.core.sensors.Sensor;

/**
 * @author Rubentxu.
 */
public class FlyingDartScript implements Script {
    private boolean init = false;

    @Override
    public void execute(ScriptController controller, ObjectMap<String, Sensor> sensors, ObjectMap<String, Actuator> actuators) {
        PropertySensor sensorFreeFlight = (PropertySensor) sensors.get("SensorFreeFlight");

        MotionActuator motionActuator = (MotionActuator) actuators.get("ActuatorFreeFlight");

        Body ownerBody =   motionActuator.targetRigidBody;

        if(!init) controller.pulseSignal = true;


           float angle =  MathUtils.atan2(ownerBody.getLinearVelocity().y ,ownerBody.getLinearVelocity().x);
           Gdx.app.log("FlyingDart","Angle dart " + angle);
           ownerBody.setTransform(ownerBody.getPosition().x,ownerBody.getPosition().y,angle);



    }

}
