package com.indignado.logicbricks.utils.logicbricks;

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
            sensorComponent = getSensorComponent(AlwaysSensorComponent.class);
            sensorsList = (Set<S>) getSensorList(AlwaysSensor.class, sensorComponent, sensor.state);

        } else if (sensor instanceof CollisionSensor) {
            getSystem(CollisionSensorSystem.class);
            sensorComponent = getSensorComponent(CollisionSensorComponent.class);
            sensorsList = (Set<S>) getSensorList(CollisionSensor.class, sensorComponent, sensor.state);

        } else if (sensor instanceof KeyboardSensor) {
            getSystem(KeyboardSensorSystem.class);
            sensorComponent = getSensorComponent(KeyboardSensorComponent.class);
            sensorsList = (Set<S>) getSensorList(KeyboardSensor.class, sensorComponent, sensor.state);

        } else if (sensor instanceof MouseSensor) {
            getSystem(MouseSensorSystem.class);
            sensorComponent = getSensorComponent(MouseSensorComponent.class);
            sensorsList = (Set<S>) getSensorList(MouseSensor.class, sensorComponent, sensor.state);

        } else if (sensor instanceof PropertySensor) {
            getSystem(PropertySensorSystem.class);
            sensorComponent = getSensorComponent(PropertySensorComponent.class);
            sensorsList = (Set<S>) getSensorList(PropertySensor.class, sensorComponent, sensor.state);

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

            if(entitySystem instanceof MouseSensorSystem)
                engine.addEntityListener(((MouseSensorSystem) entitySystem).getFamily(), (MouseSensorSystem) entitySystem);
            if(entitySystem instanceof KeyboardSensorSystem)
                engine.addEntityListener(((KeyboardSensorSystem) entitySystem).getFamily(), (KeyboardSensorSystem) entitySystem);
            if(entitySystem instanceof CollisionSensorSystem)
                engine.addEntityListener(((CollisionSensorSystem) entitySystem).getFamily(), (CollisionSensorSystem) entitySystem);

        }
        return entitySystem;

    }


    private <SC extends SensorComponent> SC getSensorComponent(Class<SC> clazz) {
        SC sensorComponent = entity.getComponent(clazz);
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


    private <S extends Sensor> Set<S> getSensorList(Class<S> clazz, SensorComponent sensorComponent, int state) {
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

        } else if (actuator instanceof EditRigidBodyActuator) {
            actuatorComponent = entity.getComponent(EditRigidBodyActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new EditRigidBodyActuatorComponent();
                entity.add(actuatorComponent);
            }
            processActuator(state, actuatorComponent);

        } else if (actuator instanceof TextureActuator) {
            actuatorComponent = entity.getComponent(TextureActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new TextureActuatorComponent();
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

        } else if (actuator instanceof PropertyActuator) {
            actuatorComponent = entity.getComponent(PropertyActuatorComponent.class);
            if (actuatorComponent == null) {
                actuatorComponent = new PropertyActuatorComponent();
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


}
