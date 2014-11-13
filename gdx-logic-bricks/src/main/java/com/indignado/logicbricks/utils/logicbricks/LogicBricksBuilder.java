package com.indignado.logicbricks.utils.logicbricks;

import com.badlogic.ashley.core.Entity;
import com.indignado.logicbricks.bricks.actuators.*;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.controllers.Controller;
import com.indignado.logicbricks.bricks.controllers.ScriptController;
import com.indignado.logicbricks.bricks.sensors.*;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.actuators.*;
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
    private final Entity entity;
    private final StateComponent stateComponent;
    private Controller controller;
    private Actuator actuator;


    public LogicBricksBuilder(Entity entity) {
        this.entity = entity;
        this.stateComponent = entity.getComponent(StateComponent.class);

    }


    private int getKeyState(String state) {
        int keyState = stateComponent.getState(state);
        if(keyState == -1) {
            keyState = stateComponent.createState(state);
        }
        return keyState;

    }


    public <S extends Sensor> LogicBricksBuilder addSensor(S sensor, String... nameStates) {
        for (String s : nameStates) {
            addSensor(sensor, s);
        }
        return this;

    }


    public <S extends Sensor> LogicBricksBuilder addSensor(S sensor, String nameState) {
        int state = getKeyState(nameState);
        sensor.state = state;
        SensorComponent sensorComponent = null;
        Set<S> sensorsList = null;
        if (sensor instanceof AlwaysSensor) {
            sensorComponent = entity.getComponent(AlwaysSensorComponent.class);
            if (sensorComponent == null) {
                sensorComponent = new AlwaysSensorComponent();
                entity.add(sensorComponent);
            }
            sensorsList = (Set<S>) sensorComponent.sensors.get(state);
            if (sensorsList == null) {
                sensorsList = (Set<S>) new HashSet<AlwaysSensor>();
                sensorComponent.sensors.put(state, sensorsList);

            }


        } else if (sensor instanceof CollisionSensor) {
            sensorComponent = entity.getComponent(CollisionSensorComponent.class);
            if (sensorComponent == null) {
                sensorComponent = new CollisionSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<CollisionSensor>());
                entity.add(sensorComponent);
            }

        } else if (sensor instanceof KeyboardSensor) {
            sensorComponent = entity.getComponent(KeyboardSensorComponent.class);
            if (sensorComponent == null) {
                sensorComponent = new KeyboardSensorComponent();
                entity.add(sensorComponent);
            }
            sensorsList = (Set<S>) sensorComponent.sensors.get(state);
            if (sensorsList == null) {
                sensorsList = (Set<S>) new HashSet<KeyboardSensor>();
                sensorComponent.sensors.put(state, sensorsList);

            }


        } else if (sensor instanceof MouseSensor) {
            sensorComponent = entity.getComponent(MouseSensorComponent.class);
            if (sensorComponent == null) {
                sensorComponent = new MouseSensorComponent();
                sensorComponent.sensors.put(state, new HashSet<MouseSensor>());
                entity.add(sensorComponent);
            }

        }
        if (sensorComponent != null) ((Set<S>) sensorComponent.sensors.get(state)).add(sensor);
        return this;

    }


    public <C extends Controller> LogicBricksBuilder addController(C controller, String... nameStates) {
        for (String s : nameStates) {
            addController(controller, s);
        }
        return this;

    }


    public <C extends Controller> LogicBricksBuilder addController(C controller, String nameState) {
        this.controller = controller;
        int state = getKeyState(nameState);
        controller.state = state;
        ControllerComponent controllerComponent = null;
        Set<C> controllerList = null;

        if (controller instanceof ConditionalController) {
            controllerComponent = entity.getComponent(ConditionalControllerComponent.class);
            if (controllerComponent == null) {
                controllerComponent = new ConditionalControllerComponent();
                entity.add(controllerComponent);
            }
            controllerList = (Set<C>) controllerComponent.controllers.get(state);
            if (controllerList == null) {
                controllerList = (Set<C>) new HashSet<ConditionalController>();
                controllerComponent.controllers.put(state, controllerList);
            }

        } else if (controller instanceof ScriptController) {
            controllerComponent = entity.getComponent(ScriptControllerComponent.class);
            if (controllerComponent == null) {
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


    public <A extends Actuator> LogicBricksBuilder addActuator(A actuator, String... nameStates) {
        for (String s : nameStates) {
            addActuator(actuator, s);
        }
        return this;

    }


    public <A extends Actuator> LogicBricksBuilder addActuator(A actuator, String nameState) {
        this.actuator = actuator;
        int state = getKeyState(nameState);
        actuator.state = state;
        ActuatorComponent actuatorComponent = null;
        Set<A> actuatorList = null;

        if (actuator instanceof CameraActuator) {
            actuatorComponent = entity.getComponent(CameraActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new CameraActuatorComponent();
                entity.add(actuatorComponent);
            }
            processActuator(state, actuatorComponent);

        } else if (actuator instanceof MessageActuator) {
            actuatorComponent = entity.getComponent(MessageActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new MessageActuatorComponent();
                entity.add(actuatorComponent);
            }
            processActuator(state, actuatorComponent);

        } else if (actuator instanceof MotionActuator) {
            actuatorComponent = entity.getComponent(MotionActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new MotionActuatorComponent();
                entity.add(actuatorComponent);
            }
            processActuator(state, actuatorComponent);

        } else if (actuator instanceof RigidBodyPropertyActuator) {
            actuatorComponent = entity.getComponent(RigidBodyPropertyActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new RigidBodyPropertyActuatorComponent();
                entity.add(actuatorComponent);
            }
            processActuator(state, actuatorComponent);

        }  else if (actuator instanceof ViewActuator) {
            actuatorComponent = entity.getComponent(ViewActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new ViewActuatorComponent();
                entity.add(actuatorComponent);
            }
            processActuator(state, actuatorComponent);

        } else if (actuator instanceof StateActuator) {
            actuatorComponent = entity.getComponent(StateActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new StateActuatorComponent();
                entity.add(actuatorComponent);
            }
            processActuator(state, actuatorComponent);

        }
        ((Set<A>) actuatorComponent.actuators.get(state)).add(actuator);
        return this;

    }


    private <A extends Actuator> void processActuator(int state, ActuatorComponent actuatorComponent) {
        Set<A> actuatorList;
        actuatorList = (Set<A>) actuatorComponent.actuators.get(state);
        if (actuatorList == null) {
            actuatorList = new HashSet<A>();
            actuatorComponent.actuators.put(state, actuatorList);
        }
    }


    public LogicBricksBuilder connect(Controller controller) {
        actuator.controllers.add(controller);
        return this;

    }


}
