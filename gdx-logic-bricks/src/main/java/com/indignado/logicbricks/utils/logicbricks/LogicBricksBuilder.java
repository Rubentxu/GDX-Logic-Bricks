package com.indignado.logicbricks.utils.logicbricks;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
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
import com.indignado.logicbricks.systems.actuators.*;
import com.indignado.logicbricks.systems.controllers.ConditionalControllerSystem;
import com.indignado.logicbricks.systems.controllers.ScriptControllerSystem;
import com.indignado.logicbricks.systems.sensors.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBricksBuilder {
    private final Engine engine;
    private Entity entity;
    private StateComponent stateComponent;
    private Controller controller;
    private Array<String> controllerStates;


    public LogicBricksBuilder(Engine engine, Entity entity) {
        this.engine = engine;
        this.entity = entity;
        this.stateComponent = entity.getComponent(StateComponent.class);
        this.controllerStates = new Array();

    }


    private int getKeyState(String state) {
        int keyState = stateComponent.getState(state);
        if (keyState == -1) {
            keyState = stateComponent.createState(state);
        }
        return keyState;

    }


    private <S extends Sensor> LogicBricksBuilder addSensors(Sensor sensor, Array<String> nameStates) {
        for (String s : nameStates) {
            addSensor(sensor, s);
        }
        return this;

    }


    private <S extends Sensor> LogicBricksBuilder addSensor(S sensor, String nameState) {
        int state = getKeyState(nameState);
        sensor.state = state;
        SensorComponent sensorComponent = null;
        Set<S> sensorsList = null;
        if (sensor instanceof AlwaysSensor) {
            getSystem(AlwaysSensorSystem.class);
            sensorComponent = getComponent(AlwaysSensorComponent.class);
            sensorsList = (Set<S>) getSensorList(sensorComponent, sensor.state);

        } else if (sensor instanceof CollisionSensor) {
            getSystem(CollisionSensorSystem.class);
            sensorComponent = getComponent(CollisionSensorComponent.class);
            sensorsList = (Set<S>) getSensorList(sensorComponent, sensor.state);

        } else if (sensor instanceof DelaySensor) {
            getSystem(DelaySensorSystem.class);
            sensorComponent = getComponent(DelaySensorComponent.class);
            sensorsList = (Set<S>) getSensorList(sensorComponent, sensor.state);

        } else if (sensor instanceof KeyboardSensor) {
            getSystem(KeyboardSensorSystem.class);
            sensorComponent = getComponent(KeyboardSensorComponent.class);
            sensorsList = (Set<S>) getSensorList(sensorComponent, sensor.state);

        } else if (sensor instanceof MouseSensor) {
            getSystem(MouseSensorSystem.class);
            sensorComponent = getComponent(MouseSensorComponent.class);
            sensorsList = (Set<S>) getSensorList(sensorComponent, sensor.state);

        } else if (sensor instanceof PropertySensor) {
            getSystem(PropertySensorSystem.class);
            sensorComponent = getComponent(PropertySensorComponent.class);
            sensorsList = (Set<S>) getSensorList(sensorComponent, sensor.state);

        }
        if (sensorsList != null && !sensorsList.contains(sensor)) sensorsList.add(sensor);

        return this;

    }


    private <ES extends EntitySystem> ES getSystem(Class<ES> clazz) {
        ES entitySystem = engine.getSystem(clazz);
        if (entitySystem == null) {
            try {
                entitySystem = clazz.newInstance();
            } catch (InstantiationException e) {
                Gdx.app.log("LogicBricksBuilder", "Error instance entitySystem " + clazz);
            } catch (IllegalAccessException e) {
                Gdx.app.log("LogicBricksBuilder", "Error instance entitySystem " + clazz);
            }
            engine.addSystem(entitySystem);

            if (entitySystem instanceof MouseSensorSystem)
                engine.addEntityListener(((MouseSensorSystem) entitySystem).getFamily(), (MouseSensorSystem) entitySystem);
            if (entitySystem instanceof KeyboardSensorSystem)
                engine.addEntityListener(((KeyboardSensorSystem) entitySystem).getFamily(), (KeyboardSensorSystem) entitySystem);
            if (entitySystem instanceof CollisionSensorSystem)
                engine.addEntityListener(((CollisionSensorSystem) entitySystem).getFamily(), (CollisionSensorSystem) entitySystem);

        }
        return entitySystem;

    }


    private <C extends Component> C getComponent(Class<C> clazz) {
        C sensorComponent = entity.getComponent(clazz);
        if (sensorComponent == null) {
            try {
                sensorComponent = clazz.newInstance();
            } catch (InstantiationException e) {
                Gdx.app.log("LogicBricksBuilder", "Error instance sensorComponent " + clazz);
            } catch (IllegalAccessException e) {
                Gdx.app.log("LogicBricksBuilder", "Error instance sensorComponent " + clazz);
            }
            entity.add(sensorComponent);
        }
        return sensorComponent;

    }


    private <S extends Sensor> Set<S> getSensorList(SensorComponent sensorComponent, int state) {
        Set<S> sensorsList = (Set<S>) sensorComponent.sensors.get(state);
        if (sensorsList == null) {
            sensorsList = new HashSet<S>();
            sensorComponent.sensors.put(state, sensorsList);
        }
        return sensorsList;

    }


    public <C extends Controller> LogicBricksBuilder addController(C controller, String... nameStates) {
        controllerStates.clear();
        for (String s : nameStates) {
            addController(controller, s);
        }
        return this;

    }


    public <C extends Controller> LogicBricksBuilder addController(C controller, String nameState) {
        this.controller = controller;
        controllerStates.add(nameState);
        int state = getKeyState(nameState);
        controller.state = state;
        ControllerComponent controllerComponent = null;
        Set<C> controllerList = null;

        if (controller instanceof ConditionalController) {
            getSystem(ConditionalControllerSystem.class);
            controllerComponent = getComponent(ConditionalControllerComponent.class);
            controllerList = (Set<C>) getControllerList(controllerComponent, state);


        } else if (controller instanceof ScriptController) {
            getSystem(ScriptControllerSystem.class);
            controllerComponent = getComponent(ScriptControllerComponent.class);
            controllerList = (Set<C>) getControllerList(controllerComponent, state);

        }
        if (controllerList != null && !controllerList.contains(controller)) controllerList.add(controller);

        return this;

    }


    private <C extends Controller> Set<C> getControllerList(ControllerComponent controllerComponent, int state) {
        Set<C> controllersList = (Set<C>) controllerComponent.controllers.get(state);
        if (controllersList == null) {
            controllersList = new HashSet<C>();
            controllerComponent.controllers.put(state, controllersList);
        }
        return controllersList;

    }


    public LogicBricksBuilder connectToSensors(Sensor... sensors) {
        for (Sensor s : sensors) {
            connectToSensor(s);
        }
        return this;

    }


    public LogicBricksBuilder connectToSensor(Sensor sensor) {
        addSensors(sensor, controllerStates);
        controller.sensors.add(sensor);
        return this;

    }


    public LogicBricksBuilder connectToActuators(Actuator... actuators) {
        for (Actuator a : actuators) {
            connectToActuator(a);
        }
        return this;

    }


    public LogicBricksBuilder connectToActuator(Actuator actuator) {
        addActuators(actuator, controllerStates);
        actuator.controllers.add(controller);
        return this;

    }


    private <A extends Actuator> LogicBricksBuilder addActuators(Actuator actuator, Array<String> nameStates) {
        for (String s : nameStates) {
            addActuator(actuator, s);
        }
        return this;

    }


    private <A extends Actuator> LogicBricksBuilder addActuator(A actuator, String nameState) {
        int state = getKeyState(nameState);
        actuator.state = state;
        ActuatorComponent actuatorComponent = null;
        Set<A> actuatorList = null;

        if (actuator instanceof CameraActuator) {
            getSystem(CameraActuatorSystem.class);
            actuatorComponent = getComponent(CameraActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        } else if (actuator instanceof EditEntityActuator) {
            getSystem(EditEntityActuatorSystem.class);
            actuatorComponent = getComponent(EditEntityActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        } else if (actuator instanceof EditRigidBodyActuator) {
            getSystem(EditRigidBodyActuatorSystem.class);
            actuatorComponent = getComponent(EditRigidBodyActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        } else if (actuator instanceof EffectActuator) {
            getSystem(EffectActuatorSystem.class);
            actuatorComponent = getComponent(EffectActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        } else if (actuator instanceof MessageActuator) {
            getSystem(MessageActuatorSystem.class);
            actuatorComponent = getComponent(MessageActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        } else if (actuator instanceof MotionActuator) {
            getSystem(MotionActuatorSystem.class);
            actuatorComponent = getComponent(MotionActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        } else if (actuator instanceof PropertyActuator) {
            getSystem(PropertyActuatorSystem.class);
            actuatorComponent = getComponent(PropertyActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        } else if (actuator instanceof StateActuator) {
            getSystem(StateActuatorSystem.class);
            actuatorComponent = getComponent(StateActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        } else if (actuator instanceof TextureActuator) {
            getSystem(TextureActuatorSystem.class);
            actuatorComponent = getComponent(TextureActuatorComponent.class);
            actuatorList = getActuatorList(actuatorComponent, state);

        }
        if (actuatorList != null && !actuatorList.contains(controller)) actuatorList.add(actuator);
        return this;

    }


    private <A extends Actuator> Set<A> getActuatorList(ActuatorComponent actuatorComponent, int state) {
        Set<A> actuatorList = (Set<A>) actuatorComponent.actuators.get(state);
        if (actuatorList == null) {
            actuatorList = new HashSet<A>();
            actuatorComponent.actuators.put(state, actuatorList);
        }
        return actuatorList;

    }


}
