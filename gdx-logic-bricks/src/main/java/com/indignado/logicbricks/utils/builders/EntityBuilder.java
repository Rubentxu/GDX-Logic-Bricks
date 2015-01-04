package com.indignado.logicbricks.utils.builders;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.data.View;
import com.indignado.logicbricks.components.sensors.SensorComponent;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.systems.sensors.KeyboardSensorSystem;
import com.indignado.logicbricks.systems.sensors.MouseSensorSystem;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public class EntityBuilder {
    private static String tag = "EntityBuilder";
    PooledEngine engine;
    private Entity entity;
    private Controller controller;
    private Array<String> controllerStates;


    public EntityBuilder(PooledEngine engine) {
        this.controllerStates = new Array();
        this.engine = engine;

    }


    private static Constructor findConstructor(Class type) {
        try {
            return ClassReflection.getConstructor(type, (Class[]) null);
        } catch (Exception ex1) {
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, (Class[]) null);
                constructor.setAccessible(true);
                return constructor;
            } catch (ReflectionException ex2) {
                Log.debug(tag, "Error instance entitySystem %s", ex2.getMessage());
                return null;
            }
        }
    }


    public EntityBuilder initialize() {
        entity = engine.createEntity();
        return this;

    }


    public EntityBuilder initialize(Entity entity) {
        this.entity = entity;
        return this;

    }


    private int getKeyState(String state) {
        StateComponent stateComponent = getComponent(StateComponent.class);

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
        ObjectSet<S> sensorsList = null;
        BricksUtils.BricksClasses classes = BricksUtils.getBricksClasses(sensor.getClass());
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
                Constructor constructor = findConstructor(clazz);
                entitySystem = (ES) constructor.newInstance((Object[]) null);
            } catch (Exception ex) {
                Log.debug(tag, "Error instance entitySystem %s", clazz.getSimpleName());

            }
            engine.addSystem(entitySystem);


        }
        return entitySystem;

    }


    public <C extends Component> C getComponent(Class<C> clazz) {
        C comp = null;
        if (entity.getComponent(clazz) != null) {
            comp = (C) entity.getComponent(clazz);
        } else {
            comp = engine.createComponent(clazz);
            entity.add(comp);
        }
        return comp;

    }


    private <S extends Sensor> ObjectSet<S> getSensorList(SensorComponent sensorComponent, int state) {
        ObjectSet<S> sensorsList = (ObjectSet<S>) sensorComponent.sensors.get(state);
        if (sensorsList == null) {
            sensorsList = new ObjectSet<S>();
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


    public <C extends Controller> EntityBuilder addController(C controller, Array<String> nameStates) {
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
        ObjectSet<C> controllerList = null;

        BricksUtils.BricksClasses classes = BricksUtils.getBricksClasses(controller.getClass());
        if (classes != null) {
            getSystem(classes.system);
            CC controllerComponent = (CC) getComponent(classes.component);
            controllerList = getControllerList(controllerComponent, controller.state);
        }
        if (controllerList != null && !controllerList.contains(controller)) controllerList.add(controller);
        return this;

    }


    private <C extends Controller> ObjectSet<C> getControllerList(ControllerComponent controllerComponent, int state) {
        ObjectSet<C> controllersList = (ObjectSet<C>) controllerComponent.controllers.get(state);
        if (controllersList == null) {
            controllersList = new ObjectSet<C>();
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
        if (sensor.name == null) sensor.name = sensor.getClass().getSimpleName() + "_" + MathUtils.random(10000);
        controller.sensors.put(sensor.name, sensor);
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
        if (actuator.name == null)
            actuator.name = actuator.getClass().getSimpleName() + "_" + MathUtils.random(10000);
        ;
        actuator.controllers.add(controller);
        controller.actuators.put(actuator.name, actuator);
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
        ObjectSet<A> actuatorList = null;

        BricksUtils.BricksClasses classes = BricksUtils.getBricksClasses(actuator.getClass());
        if (classes != null) {
            getSystem(classes.system);
            AC actuatorComponent = (AC) getComponent(classes.component);
            actuatorList = getActuatorList(actuatorComponent, actuator.state);
        }

        if (actuatorList != null && !actuatorList.contains(actuator)) actuatorList.add(actuator);
        return this;

    }


    private <A extends Actuator> ObjectSet<A> getActuatorList(ActuatorComponent actuatorComponent, int state) {
        ObjectSet<A> actuatorList = (ObjectSet<A>) actuatorComponent.actuators.get(state);
        if (actuatorList == null) {
            actuatorList = new ObjectSet<A>();
            actuatorComponent.actuators.put(state, actuatorList);
        }
        return actuatorList;

    }


    public <V extends View> EntityBuilder addView(V view) {
        getComponent(ViewsComponent.class).views.add(view);
        return this;

    }


    public EntityBuilder addRigidBody(Body rigidBody) {
        getComponent(RigidBodiesComponents.class).rigidBodies.add(rigidBody);
        return this;

    }


    public EntityBuilder addProperty(Property property) {
        getComponent(BlackBoardComponent.class).addProperty(property);
        return this;

    }


    private void config(IntMap<ObjectSet<LogicBrick>> bricks, Entity entity) {
        for (int i = 0; i < bricks.size; i++) {
            for (LogicBrick brick : (ObjectSet<LogicBrick>) bricks.get(i)) {
                brick.owner = entity;
            }
        }

    }


    public Entity getEntity() {
        for (Component c : entity.getComponents()) {
            if (c instanceof SensorComponent) {
                config(((SensorComponent) c).sensors, entity);
            }
            if (c instanceof ControllerComponent) {
                config(((ControllerComponent) c).controllers, entity);
            }
            if (c instanceof ActuatorComponent) {
                config(((ActuatorComponent) c).actuators, entity);
            }
        }
        controllerStates.clear();
        return entity;

    }

}
