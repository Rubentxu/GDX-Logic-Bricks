package com.indignado.logicbricks.utils.builders;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.components.RadialGravityComponent;
import com.indignado.logicbricks.components.actuators.*;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;
import com.indignado.logicbricks.components.sensors.*;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.actuators.*;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.*;
import com.indignado.logicbricks.systems.BuoyancySystem;
import com.indignado.logicbricks.systems.RadialGravitySystem;
import com.indignado.logicbricks.systems.actuators.*;
import com.indignado.logicbricks.systems.controllers.ConditionalControllerSystem;
import com.indignado.logicbricks.systems.controllers.ScriptControllerSystem;
import com.indignado.logicbricks.systems.sensors.*;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public class EngineUtils {
    private static String tag = "EngineUtils";
    private static ObjectMap<Class<? extends BrickBuilder>, BrickBuilder> buildersMap = new ObjectMap<>();
    private static ObjectMap<Class<? extends LogicBrick>, BricksClasses> bricksClasses = new ObjectMap<>();
    private static ObjectMap<Class<? extends Component>, Class<? extends EntitySystem>> engineClasses = new ObjectMap<>();
    private static EngineUtils instance;


    private EngineUtils() {
        bricksClasses.put(AlwaysSensor.class, new BricksClasses(AlwaysSensorComponent.class, AlwaysSensorSystem.class));
        bricksClasses.put(CollisionSensor.class, new BricksClasses(CollisionSensorComponent.class, CollisionSensorSystem.class));
        bricksClasses.put(KeyboardSensor.class, new BricksClasses(KeyboardSensorComponent.class, KeyboardSensorSystem.class));
        bricksClasses.put(MouseSensor.class, new BricksClasses(MouseSensorComponent.class, MouseSensorSystem.class));
        bricksClasses.put(PropertySensor.class, new BricksClasses(PropertySensorComponent.class, PropertySensorSystem.class));
        bricksClasses.put(MessageSensor.class, new BricksClasses(MessageSensorComponent.class, MessageSensorSystem.class));
        bricksClasses.put(DelaySensor.class, new BricksClasses(DelaySensorComponent.class, DelaySensorSystem.class));
        bricksClasses.put(RadarSensor.class, new BricksClasses(RadarSensorComponent.class, RadarSensorSystem.class));
        bricksClasses.put(NearSensor.class, new BricksClasses(NearSensorComponent.class, NearSensorSystem.class));
        bricksClasses.put(RaySensor.class, new BricksClasses(RaySensorComponent.class, RaySensorSystem.class));

        bricksClasses.put(ConditionalController.class, new BricksClasses(ConditionalControllerComponent.class, ConditionalControllerSystem.class));
        bricksClasses.put(ScriptController.class, new BricksClasses(ScriptControllerComponent.class, ScriptControllerSystem.class));

        bricksClasses.put(CameraActuator.class, new BricksClasses(CameraActuatorComponent.class, CameraActuatorSystem.class));
        bricksClasses.put(EditRigidBodyActuator.class, new BricksClasses(EditRigidBodyActuatorComponent.class, EditRigidBodyActuatorSystem.class));
        bricksClasses.put(EffectActuator.class, new BricksClasses(EffectActuatorComponent.class, EffectActuatorSystem.class));
        bricksClasses.put(InstanceEntityActuator.class, new BricksClasses(InstanceEntityActuatorComponent.class, InstanceEntityActuatorSystem.class));
        bricksClasses.put(MessageActuator.class, new BricksClasses(MessageActuatorComponent.class, MessageActuatorSystem.class));
        bricksClasses.put(MotionActuator.class, new BricksClasses(MotionActuatorComponent.class, MotionActuatorSystem.class));
        bricksClasses.put(PropertyActuator.class, new BricksClasses(PropertyActuatorComponent.class, PropertyActuatorSystem.class));
        bricksClasses.put(StateActuator.class, new BricksClasses(StateActuatorComponent.class, StateActuatorSystem.class));
        bricksClasses.put(TextureActuator.class, new BricksClasses(TextureActuatorComponent.class, TextureActuatorSystem.class));

        engineClasses.put(BuoyancyComponent.class, BuoyancySystem.class);
        engineClasses.put(RadialGravityComponent.class, RadialGravitySystem.class);

    }


    private static synchronized void checkInstance() {
        if (instance == null) {
            instance = new EngineUtils();
        }
    }


    public static <B extends BrickBuilder> B getBuilder(Class<B> clazzBuilder) {
        B builder = (B) buildersMap.get(clazzBuilder);
        if (builder == null) {
            synchronized (clazzBuilder) {
                try {
                    Constructor constructor = findConstructor(clazzBuilder);
                    builder = (B) constructor.newInstance((Object[]) null);
                    buildersMap.put(clazzBuilder, builder);
                } catch (Exception e) {
                    Log.debug(tag, "Error instance actuatorBuilder %s" + clazzBuilder.getSimpleName());
                }
            }
        }
        return builder;

    }


    private static <B extends BrickBuilder> Constructor findConstructor(Class<B> type) {
        try {
            return ClassReflection.getConstructor(type, (Class[]) null);
        } catch (Exception ex1) {
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, (Class[]) null);
                constructor.setAccessible(true);
                return constructor;
            } catch (ReflectionException ex2) {
                return null;
            }
        }
    }


    public static <L extends LogicBrick> BricksClasses getBricksClasses(Class<L> clazz) {
        checkInstance();
        return bricksClasses.get(clazz);

    }


    public static Class<? extends EntitySystem> getSystemClass(Class<? extends Component> clazz) {
        checkInstance();
        return engineClasses.get(clazz);

    }


    class BricksClasses<C extends Component, S extends EntitySystem> {
        public Class<C> component;
        public Class<S> system;

        BricksClasses(Class<C> component, Class<S> system) {
            this.system = system;
            this.component = component;

        }

    }


}
