package com.indignado.logicbricks.utils.logicbricks;

import com.badlogic.ashley.core.Component;
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
            sensorComponent = entity.getComponent(AlwaysSensorComponent.class);
            if(sensorComponent == null){
                sensorComponent = new AlwaysSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<AlwaysSensor>());
                entity.add(sensorComponent);
            }

        } else if (sensor instanceof CollisionSensor) {
            sensorComponent = entity.getComponent(CollisionSensorComponent.class);
            if(sensorComponent == null){
                sensorComponent = new CollisionSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<CollisionSensor>());
                entity.add(sensorComponent);
            }

        } else if (sensor instanceof KeyboardSensor) {
            sensorComponent = entity.getComponent(KeyboardSensorComponent.class);
            if(sensorComponent == null){
                sensorComponent = new KeyboardSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<KeyboardSensor>());
                entity.add(sensorComponent);
            }

        } else if (sensor instanceof MouseSensor) {
            sensorComponent = entity.getComponent(MouseSensorComponent.class);
            if(sensorComponent == null){
                sensorComponent = new MouseSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<MouseSensor>());
                entity.add(sensorComponent);
            }

        }
        ((Set<S>) sensorComponent.sensors.get(state)).add(sensor);
        return this;

    }


    public <C extends Controller> LogicBricksBuilder addController(C controller, int state) {
        this.controller = controller;
        ControllerComponent controllerComponent = null;
        if (controller instanceof ConditionalController) {
            controllerComponent = entity.getComponent(ConditionalControllerComponent.class);
            if(controllerComponent == null){
                controllerComponent = new ConditionalControllerComponent();
                controllerComponent.controllers.put(state, new HashSet<ConditionalController>());
                entity.add(controllerComponent);
            }

        } else if (controller instanceof ScriptController) {
            controllerComponent = entity.getComponent(ScriptControllerComponent.class);
            if(controllerComponent == null){
                controllerComponent = new ScriptControllerComponent();
                controllerComponent.controllers.put(state, new HashSet<ScriptController>());
                entity.add(controllerComponent);
            }
        }
        ((Set<C>) controllerComponent.controllers.get(state)).add(controller);
        return this;

    }


    public LogicBricksBuilder connect(Sensor sensor) {
        controller.sensors.add(sensor);
        return this;

    }


    public <A extends Actuator> LogicBricksBuilder addActuator(A actuator, int state) {
        this.actuator = actuator;
        ActuatorComponent actuatorComponent = null;

        if (actuator instanceof CameraActuator) {
            actuatorComponent = entity.getComponent(CameraActuatorComponent.class);
            if(actuatorComponent == null){
                actuatorComponent = new CameraActuatorComponent();
                actuatorComponent.actuators.put(state, new HashSet<CameraActuator>());
                entity.add(actuatorComponent);
            }

        } else if (actuator instanceof MessageActuator) {
            actuatorComponent = entity.getComponent(MessageActuatorComponent.class);
            if(actuatorComponent == null){
                actuatorComponent = new MessageActuatorComponent();
                actuatorComponent.actuators.put(state, new HashSet<MessageActuator>());
                entity.add(actuatorComponent);
            }

        } else if (actuator instanceof MotionActuator) {
            actuatorComponent = entity.getComponent(MotionActuatorComponent.class);
            if(actuatorComponent == null){
                actuatorComponent = new MotionActuatorComponent();
                actuatorComponent.actuators.put(state, new HashSet<MotionActuator>());
                entity.add(actuatorComponent);
            }

        }
        ((Set<A>) actuatorComponent.actuators.get(state)).add(actuator);
        return this;

    }


    public LogicBricksBuilder connect(Controller controller) {
        actuator.controllers.add(controller);
        return this;

    }


}
