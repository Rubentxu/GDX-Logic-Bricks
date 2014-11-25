package com.indignado.logicbricks.utils.builders;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.actuators.*;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;
import com.indignado.logicbricks.components.sensors.*;
import com.indignado.logicbricks.core.actuators.*;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.*;
import com.indignado.logicbricks.systems.actuators.*;
import com.indignado.logicbricks.systems.controllers.ConditionalControllerSystem;
import com.indignado.logicbricks.systems.controllers.ControllerSystem;
import com.indignado.logicbricks.systems.controllers.ScriptControllerSystem;
import com.indignado.logicbricks.systems.sensors.*;

/**
 * @author Rubentxu.
 */
public class AssociatedClasses {
    private final ObjectMap<Class<? extends Sensor>, SensorClasses> sensorsClasses;
    private final ObjectMap<Class<? extends Controller>, ControllerClasses> controllersClasses;
    private final ObjectMap<Class<? extends Actuator>, ActuatorClasses> actuatorsClasses;


    public AssociatedClasses() {
        sensorsClasses = new ObjectMap<>();
        sensorsClasses.put(AlwaysSensor.class, new SensorClasses<AlwaysSensorComponent, AlwaysSensorSystem>());
        sensorsClasses.put(CollisionSensor.class, new SensorClasses<CollisionSensorComponent, CollisionSensorSystem>());
        sensorsClasses.put(DelaySensor.class, new SensorClasses<DelaySensorComponent, DelaySensorSystem>());
        sensorsClasses.put(KeyboardSensor.class, new SensorClasses<KeyboardSensorComponent, KeyboardSensorSystem>());
        sensorsClasses.put(MouseSensor.class, new SensorClasses<MouseSensorComponent, MouseSensorSystem>());
        sensorsClasses.put(PropertySensor.class, new SensorClasses<PropertySensorComponent, PropertySensorSystem>());

        controllersClasses = new ObjectMap<>();
        controllersClasses.put(ConditionalController.class, new ControllerClasses<ConditionalControllerComponent, ConditionalControllerSystem>());
        controllersClasses.put(ScriptController.class, new ControllerClasses<ScriptControllerComponent, ScriptControllerSystem>());

        actuatorsClasses = new ObjectMap<>();
        actuatorsClasses.put(CameraActuator.class, new ActuatorClasses<CameraActuatorComponent, CameraActuatorSystem>());
        actuatorsClasses.put(InstanceEntityActuator.class, new ActuatorClasses<InstanceEntityActuatorComponent, InstanceEntityActuatorSystem>());
        actuatorsClasses.put(EditRigidBodyActuator.class, new ActuatorClasses<EditRigidBodyActuatorComponent, EditRigidBodyActuatorSystem>());
        actuatorsClasses.put(EffectActuator.class, new ActuatorClasses<EffectActuatorComponent, EffectActuatorSystem>());
        actuatorsClasses.put(MessageActuator.class, new ActuatorClasses<MessageActuatorComponent, MessageActuatorSystem>());
        actuatorsClasses.put(MotionActuator.class, new ActuatorClasses<MotionActuatorComponent, MotionActuatorSystem>());
        actuatorsClasses.put(PropertyActuator.class, new ActuatorClasses<PropertyActuatorComponent, PropertyActuatorSystem>());
        actuatorsClasses.put(StateActuator.class, new ActuatorClasses<StateActuatorComponent, StateActuatorSystem>());
        actuatorsClasses.put(TextureActuator.class, new ActuatorClasses<TextureActuatorComponent, TextureActuatorSystem>());

    }


    public <S extends Sensor> SensorClasses getSensorClasses(Class<S> clazz) {
        return sensorsClasses.get(clazz);

    }


    public <C extends Controller> ControllerClasses getControllerClasses(Class<C> clazz) {
        return controllersClasses.get(clazz);

    }


    public <A extends Actuator> ActuatorClasses getActuatorClasses(Class<A> clazz) {
        return actuatorsClasses.get(clazz);

    }


    class Classes<C extends Component, S extends EntitySystem> {
        public Class<C> component;
        public Class<S> system;

    }


    class SensorClasses<C extends SensorComponent, S extends SensorSystem> extends Classes {
    }

    class ControllerClasses<C extends ControllerComponent, S extends ControllerSystem> extends Classes {
    }

    class ActuatorClasses<C extends ActuatorComponent, S extends ActuatorSystem> extends Classes {
    }


}
