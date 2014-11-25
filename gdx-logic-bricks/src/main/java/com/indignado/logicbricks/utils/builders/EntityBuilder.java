package com.indignado.logicbricks.utils.builders;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.data.RigidBody;
import com.indignado.logicbricks.components.data.View;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.systems.sensors.KeyboardSensorSystem;
import com.indignado.logicbricks.systems.sensors.MouseSensorSystem;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class EntityBuilder {
    private Engine engine;
    private ObjectMap<Class<? extends Component>, Component> components;
    private Controller controller;
    private Array<String> controllerStates;
    private AssociatedClasses associatedClasses;


    public EntityBuilder(Engine engine) {
        this.components = new ObjectMap<>();
        this.controllerStates = new Array();
        this.engine = engine;
        this.associatedClasses = new AssociatedClasses();

    }


    private int getKeyState(String state) {
        StateComponent stateComponent = null;
        if (components.containsKey(StateComponent.class)) {
            stateComponent = (StateComponent) components.get(StateComponent.class);
        } else {
            stateComponent = new StateComponent();
            components.put(StateComponent.class, stateComponent);
        }

        int keyState = stateComponent.getState(state);
        if (keyState == -1) {
            keyState = stateComponent.createState(state);
        }
        return keyState;

    }


    private <S extends Sensor> EntityBuilder addSensor(Sensor sensor, Array<String> nameStates) {
        for (String s : nameStates) {
            addSensor(sensor, s);
        }
        return this;

    }


    private <S extends Sensor, SC extends SensorComponent> EntityBuilder addSensor(S sensor, String nameState) {
        int state = getKeyState(nameState);
        sensor.state = state;
        Set<S> sensorsList = null;
        AssociatedClasses.SensorClasses classes = associatedClasses.getSensorClasses(sensor.getClass());
        if (classes != null) {
            getSystem(classes.system);
            SC sensorComponent = (SC) getComponent(classes.component);
            sensorsList = getSensorList(sensorComponent, sensor.state);
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


    public <C extends Component> C getComponent(Class<C> clazz) {
        C comp = null;
        try {
            if (components.containsKey(clazz)) {
                comp = (C) components.get(clazz);
            } else {
                comp = clazz.newInstance();
                components.put(clazz, comp);
            }

        } catch (InstantiationException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance Component " + clazz);
        } catch (IllegalAccessException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance Component " + clazz);
        }
        return comp;

    }


    private <S extends Sensor> Set<S> getSensorList(SensorComponent sensorComponent, int state) {
        Set<S> sensorsList = (Set<S>) sensorComponent.sensors.get(state);
        if (sensorsList == null) {
            sensorsList = new HashSet<S>();
            sensorComponent.sensors.put(state, sensorsList);
        }
        return sensorsList;

    }


    public <C extends Controller> EntityBuilder addController(C controller, String... nameStates) {
        controllerStates.clear();
        for (String s : nameStates) {
            addController(controller, s);
        }
        return this;

    }


    public <C extends Controller, CC extends ControllerComponent> EntityBuilder addController(C controller, String nameState) {
        this.controller = controller;
        controllerStates.add(nameState);
        int state = getKeyState(nameState);
        controller.state = state;
        Set<C> controllerList = null;

        AssociatedClasses.ControllerClasses classes = associatedClasses.getControllerClasses(controller.getClass());
        if (classes != null) {
            getSystem(classes.system);
            CC controllerComponent = (CC) getComponent(classes.component);
            controllerList = getControllerList(controllerComponent, controller.state);
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


    public EntityBuilder connectToSensors(Sensor... sensors) {
        for (Sensor s : sensors) {
            connectToSensor(s);
        }
        return this;

    }


    public EntityBuilder connectToSensor(Sensor sensor) {
        addSensor(sensor, controllerStates);
        controller.sensors.add(sensor);
        return this;

    }


    public EntityBuilder connectToActuators(Actuator... actuators) {
        for (Actuator a : actuators) {
            connectToActuator(a);
        }
        return this;

    }


    public EntityBuilder connectToActuator(Actuator actuator) {
        addActuators(actuator, controllerStates);
        actuator.controllers.add(controller);
        return this;

    }


    private <A extends Actuator> EntityBuilder addActuators(Actuator actuator, Array<String> nameStates) {
        for (String s : nameStates) {
            addActuator(actuator, s);
        }
        return this;

    }


    private <A extends Actuator, AC extends ActuatorComponent> EntityBuilder addActuator(A actuator, String nameState) {
        int state = getKeyState(nameState);
        actuator.state = state;
        Set<A> actuatorList = null;

        AssociatedClasses.ActuatorClasses classes = associatedClasses.getActuatorClasses(actuator.getClass());
        if (classes != null) {
            getSystem(classes.system);
            AC actuatorComponent = (AC) getComponent(classes.component);
            actuatorList = getActuatorList(actuatorComponent, actuator.state);
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


    public <S extends Sensor> S sensor(Class<S> clazz) {
        S sensor = null;
        try {
            sensor = clazz.newInstance();
        } catch (InstantiationException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance sensor " + clazz);
        } catch (IllegalAccessException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance sensor " + clazz);
        }
        return sensor;

    }


    public <C extends Controller> C controller(Class<C> clazz) {
        C controller = null;
        try {
            controller = clazz.newInstance();
        } catch (InstantiationException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance sensor " + clazz);
        } catch (IllegalAccessException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance sensor " + clazz);
        }
        return controller;

    }


    public <A extends Actuator> A actuator(Class<A> clazz) {
        A actuator = null;
        try {
            actuator = clazz.newInstance();
        } catch (InstantiationException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance sensor " + clazz);
        } catch (IllegalAccessException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance sensor " + clazz);
        }
        return actuator;
    }


    public <V extends View> EntityBuilder addView(V view) {
        getComponent(ViewsComponent.class).views.add(view);
        return this;

    }


    public EntityBuilder addRigidBody(RigidBody rigidBody) {
        getComponent(RigidBodiesComponents.class).rigidBodies.add(rigidBody);
        return this;

    }


    public EntityBuilder addProperty(Property property) {
        getComponent(BlackBoardComponent.class).addProperty(property);
        return this;

    }


    public Entity build() {
        Entity entity = new Entity();
        for (Component c : components.values()) {
            entity.add(c);
        }
        components.clear();
        controllerStates.clear();
        return entity;

    }


    public Entity build(Entity entity) {
        for (Component c : components.values()) {
            entity.add(c);
        }
        controller = null;
        components.clear();
        controllerStates.clear();
        return entity;

    }

}
