package com.indignado.logicbricks.utils.builders;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
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
import com.indignado.logicbricks.utils.builders.controllers.ControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.*;

/**
 * @author Rubentxu.
 */
public class BricksUtils {
    private static ObjectMap<Class<? extends BrickBuilder>, BrickBuilder> buildersMap = new ObjectMap<>();
    private static ObjectMap<Class<? extends Sensor>, SensorClasses> sensorsClasses = new ObjectMap<>();
    private static ObjectMap<Class<? extends Controller>, ControllerClasses> controllersClasses = new ObjectMap<>();
    private static ObjectMap<Class<? extends Actuator>, ActuatorClasses> actuatorsClasses = new ObjectMap<>();
    private static BricksUtils instance;

    
    private BricksUtils() {
        sensorsClasses.put(AlwaysSensor.class, new SensorClasses<AlwaysSensorComponent, AlwaysSensorSystem>());
        sensorsClasses.put(CollisionSensor.class, new SensorClasses<CollisionSensorComponent, CollisionSensorSystem>());
        sensorsClasses.put(DelaySensor.class, new SensorClasses<DelaySensorComponent, DelaySensorSystem>());
        sensorsClasses.put(KeyboardSensor.class, new SensorClasses<KeyboardSensorComponent, KeyboardSensorSystem>());
        sensorsClasses.put(MouseSensor.class, new SensorClasses<MouseSensorComponent, MouseSensorSystem>());
        sensorsClasses.put(PropertySensor.class, new SensorClasses<PropertySensorComponent, PropertySensorSystem>());

        controllersClasses.put(ConditionalController.class, new ControllerClasses<ConditionalControllerComponent, ConditionalControllerSystem>());
        controllersClasses.put(ScriptController.class, new ControllerClasses<ScriptControllerComponent, ScriptControllerSystem>());

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


    private static synchronized void checkInstance() {
        if (instance == null) {
            instance = new BricksUtils();
        }
    }


    public static <B extends BrickBuilder> B getBuilder(Class<B> clazzBuilder) {
        B builder = (B) buildersMap.get(clazzBuilder);
        if (builder == null) {
            synchronized (clazzBuilder) {
                try {
                    builder = clazzBuilder.newInstance();
                    buildersMap.put(clazzBuilder, builder);
                } catch (Exception e) {
                    Gdx.app.log("ActuatorBuilder", "Error instance actuatorBuilder " + clazzBuilder);
                }
            }
        }
        return builder;

    }


    public static <S extends Sensor> SensorClasses getSensorClasses(Class<S> clazz) {
        checkInstance();
        return sensorsClasses.get(clazz);

    }


    public static <C extends Controller> ControllerClasses getControllerClasses(Class<C> clazz) {
        checkInstance();
        return controllersClasses.get(clazz);

    }


    public static <A extends Actuator> ActuatorClasses getActuatorClasses(Class<A> clazz) {
        checkInstance();
        return actuatorsClasses.get(clazz);

    }


    class Classes<C extends Component, S extends EntitySystem> {
        public Class<C> component;
        public Class<S> system;

    }


    public class SensorClasses<C extends SensorComponent, S extends SensorSystem> extends Classes {
    }

    public class ControllerClasses<C extends ControllerComponent, S extends ControllerSystem> extends Classes {
    }

    public class ActuatorClasses<C extends ActuatorComponent, S extends ActuatorSystem> extends Classes {
    }


}
