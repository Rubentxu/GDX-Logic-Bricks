package com.indignado.logicbricks.utils.logicbricks;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.actuators.Actuator;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.bricks.actuators.MotionActuator;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.controllers.ScriptController;
import com.indignado.logicbricks.bricks.sensors.*;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;
import com.indignado.logicbricks.components.actuators.CameraActuatorComponent;
import com.indignado.logicbricks.components.actuators.MessageActuatorComponent;
import com.indignado.logicbricks.components.actuators.MotionActuatorComponent;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;
import com.indignado.logicbricks.components.sensors.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBricksBuilder {
    private Entity entity;
    private Controller controller;
    private Actuator actuator;


    public LogicBricksBuilder(Entity entity) {
        this.entity = entity;

    }


    public <S extends Sensor> LogicBricksBuilder addSensor(S sensor, int state) {
        SensorComponent sensorComponent = null;
        if (sensor instanceof AlwaysSensor) {
            if (!addSensor(AlwaysSensorComponent.class, sensor, state)) {
                sensorComponent = new AlwaysSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<AlwaysSensor>());
                entity.add(sensorComponent);
            }

        } else if (sensor instanceof CollisionSensor) {
            if (!addSensor(CollisionSensorComponent.class, sensor, state)) {
                sensorComponent = new CollisionSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<CollisionSensor>());
                entity.add(sensorComponent);
            }

        } else if (sensor instanceof KeyboardSensor) {
            if (!addSensor(KeyboardSensorComponent.class, sensor, state)) {
                sensorComponent = new KeyboardSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<KeyboardSensor>());
                entity.add(sensorComponent);
            }

        } else if (sensor instanceof MouseSensor) {
            if (!addSensor(MouseSensorComponent.class, sensor, state)) {
                sensorComponent = new MouseSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<MouseSensor>());
                entity.add(sensorComponent);
            }

        }
        return this;

    }


    private <SC extends SensorComponent, S extends Sensor> boolean addSensor(Class<SC> clazz, S sensor, int state) {
        SensorComponent sensorComponent = entity.getComponent(clazz);
        if (sensorComponent != null) {
            ((Set<S>) sensorComponent.sensors.get(state)).add(sensor);
            return true;
        } else {
            return false;
        }
    }


    public <C extends Controller> LogicBricksBuilder addController(C controller, int state) {
        this.controller = controller;
        ControllerComponent controllerComponent = null;
        if (controller instanceof ConditionalController) {
            if (!addController(ConditionalControllerComponent.class, controller, state)) {
                controllerComponent = new ConditionalControllerComponent();
                controllerComponent.controllers.put(state, new HashSet<ConditionalController>());
                entity.add(controllerComponent);
            }

        } else if (controller instanceof ScriptController) {
            if (!addController(ScriptControllerComponent.class, controller, state)) {
                controllerComponent = new ScriptControllerComponent();
                controllerComponent.controllers.put(state, new HashSet<ScriptController>());
                entity.add(controllerComponent);
            }
        }
        return this;

    }


    private <CC extends ControllerComponent, C extends Controller> boolean addController(Class<CC> clazz, C controller, int state) {
        ControllerComponent controllerComponent = entity.getComponent(clazz);
        if (controllerComponent != null) {
            ((Set<C>) controllerComponent.controllers.get(state)).add(controller);
            return true;
        } else {
            return false;
        }
    }


    public LogicBricksBuilder connect(Sensor sensor) {
        controller.sensors.add(sensor);
        return this;

    }


    public <A extends Actuator> LogicBricksBuilder addActuator(A actuator, int state) {
        this.actuator = actuator;
        ActuatorComponent actuatorComponent = null;
        if (actuator instanceof CameraActuator && !addActuator(CameraActuatorComponent.class, actuator, state)) {
            actuatorComponent = new CameraActuatorComponent();
            actuatorComponent.actuators.put(state, new HashSet<CameraActuator>());
            entity.add(actuatorComponent);

        } else if (actuator instanceof MessageActuator && !addActuator(MessageActuatorComponent.class, actuator, state)) {
            actuatorComponent = new MessageActuatorComponent();
            actuatorComponent.actuators.put(state, new HashSet<MessageActuator>());
            entity.add(actuatorComponent);

        } else if (actuator instanceof MotionActuator && !addActuator(MotionActuatorComponent.class, actuator, state)) {
            actuatorComponent = new MotionActuatorComponent();
            actuatorComponent.actuators.put(state, new HashSet<MotionActuator>());
            entity.add(actuatorComponent);

        }
        return this;

    }


    private <AC extends ActuatorComponent, A extends Actuator> boolean addActuator(Class<AC> clazz, A actuator, int state) {
        ActuatorComponent actuatorComponent = entity.getComponent(clazz);
        if (actuatorComponent != null) {
            ((Set<A>) actuatorComponent.actuators.get(state)).add(actuator);
            return true;
        } else {
            return false;
        }
    }


    public LogicBricksBuilder connect(Controller controller) {
        actuator.controllers.add(controller);
        return this;

    }


}
