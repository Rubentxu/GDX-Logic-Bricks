package com.indignado.logicbricks.config;

import box2dLight.RayHandler;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.components.actuators.*;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.components.controllers.ControllerComponent;
import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;
import com.indignado.logicbricks.components.sensors.*;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.LogicBricksException;
import com.indignado.logicbricks.core.actuators.*;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.controllers.Controller;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.*;
import com.indignado.logicbricks.systems.*;
import com.indignado.logicbricks.systems.actuators.*;
import com.indignado.logicbricks.systems.controllers.ConditionalControllerSystem;
import com.indignado.logicbricks.systems.controllers.ControllerSystem;
import com.indignado.logicbricks.systems.controllers.ScriptControllerSystem;
import com.indignado.logicbricks.systems.sensors.*;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class GameContext implements ContextProviders {
    public ObjectMap<Class,Object> context = new ObjectMap<>();


    public <T> T get (Class<T> clazz) {
        if (!context.containsKey(clazz)) throw new LogicBricksException("GameContext ",clazz.getSimpleName() + " not exist");
        return (T) context.get(clazz);

    }


    public void load() {
        context.put(Batch.class, provideBatch());
        context.put(AssetManager.class, provideAssetManager());
        context.put(World.class, providePhysics());
        context.put(LogicBricksEngine.class, provideEngine());
        registerDefaultClasses();
        context.put(Camera.class, provideCamera());
        context.put(RayHandler.class, provideRayHandler(get(World.class)));
        context.put(LBBuilders.class, provideUtilBuilder(get(LogicBricksEngine.class),get(World.class), get(RayHandler.class)));
        context.put(Game.class, provideGame(get(LogicBricksEngine.class), get(World.class)));

    }


    @Override
    public Game provideGame(LogicBricksEngine engine, World physics) {
        return new Game(engine, physics);

    }


    @Override
    public AssetManager provideAssetManager() {
        return new AssetManager();

    }


    @Override
    public <T> T providePhysics() {
        return (T) new World(Settings.GRAVITY,true);

    }


    @Override
    public LogicBricksEngine provideEngine() {
        LogicBricksEngine engine =  new LogicBricksEngine(this);

        return engine;

    }


    @Override
    public Camera provideCamera() {
        return new OrthographicCamera();

    }


    @Override
    public Batch provideBatch() {
        return new SpriteBatch();

    }


    @Override
    public <T> LBBuilders provideUtilBuilder(LogicBricksEngine engine, T physics, RayHandler rayHandler) {
        return new LBBuilders(engine, (World) physics, rayHandler);

    }


    @Override
    public <T> RayHandler provideRayHandler(T physics) {
        return new RayHandler((World) physics);
    }





    @Override
    public void registerDefaultClasses() {
        LogicBricksEngine engine = get(LogicBricksEngine.class);
        engine.registerBricksClasses(AlwaysSensor.class, AlwaysSensorComponent.class, AlwaysSensorSystem.class);
        engine.registerBricksClasses(CollisionSensor.class, CollisionSensorComponent.class, CollisionSensorSystem.class);
        engine.registerBricksClasses(KeyboardSensor.class, KeyboardSensorComponent.class, KeyboardSensorSystem.class);
        engine.registerBricksClasses(MouseSensor.class, MouseSensorComponent.class, MouseSensorSystem.class);
        engine.registerBricksClasses(PropertySensor.class, PropertySensorComponent.class, PropertySensorSystem.class);
        engine.registerBricksClasses(MessageSensor.class, MessageSensorComponent.class, MessageSensorSystem.class);
        engine.registerBricksClasses(DelaySensor.class, DelaySensorComponent.class, DelaySensorSystem.class);
        engine.registerBricksClasses(RadarSensor.class, RadarSensorComponent.class, RadarSensorSystem.class);
        engine.registerBricksClasses(NearSensor.class, NearSensorComponent.class, NearSensorSystem.class);
        engine.registerBricksClasses(RaySensor.class, RaySensorComponent.class, RaySensorSystem.class);

        engine.registerBricksClasses(Controller.class, ControllerComponent.class, ControllerSystem.class);
        engine.registerBricksClasses(ConditionalController.class, ConditionalControllerComponent.class, ConditionalControllerSystem.class);
        engine.registerBricksClasses(ScriptController.class, ScriptControllerComponent.class, ScriptControllerSystem.class);

        engine.registerBricksClasses(CameraActuator.class, CameraActuatorComponent.class, CameraActuatorSystem.class);
        engine.registerBricksClasses(EditRigidBodyActuator.class, EditRigidBodyActuatorComponent.class, EditRigidBodyActuatorSystem.class);
        engine.registerBricksClasses(Effect2DActuator.class, EffectActuatorComponent.class, EffectActuatorSystem.class);
        engine.registerBricksClasses(InstanceEntityActuator.class, InstanceEntityActuatorComponent.class, InstanceEntityActuatorSystem.class);
        engine.registerBricksClasses(MessageActuator.class, MessageActuatorComponent.class, MessageActuatorSystem.class);
        engine.registerBricksClasses(MotionActuator.class, MotionActuatorComponent.class, MotionActuatorSystem.class);
        engine.registerBricksClasses(PropertyActuator.class, PropertyActuatorComponent.class, PropertyActuatorSystem.class);
        engine.registerBricksClasses(StateActuator.class, StateActuatorComponent.class, StateActuatorSystem.class);
        engine.registerBricksClasses(TextureActuator.class, TextureActuatorComponent.class, TextureActuatorSystem.class);

        engine.registerEngineClasses(BuoyancyComponent.class, BuoyancySystem.class);
        engine.registerEngineClasses(RadialGravityComponent.class, RadialGravitySystem.class);
        engine.registerEngineClasses(ViewsComponent.class, RenderingSystem.class, AnimationViewSystem.class);
        engine.registerEngineClasses(StateComponent.class, StateSystem.class);
        engine.registerEngineClasses(LightComponent.class, LightRenderingSystem.class);
        engine.registerEngineClasses(TransformsComponent.class, TransformSystem.class);

    }

}
