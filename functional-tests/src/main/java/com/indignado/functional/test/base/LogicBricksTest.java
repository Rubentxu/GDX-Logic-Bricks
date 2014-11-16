package com.indignado.functional.test.base;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.indignado.logicbricks.systems.AnimationSystem;
import com.indignado.logicbricks.systems.RenderingSystem;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.systems.actuators.*;
import com.indignado.logicbricks.systems.controllers.ConditionalControllerSystem;
import com.indignado.logicbricks.systems.controllers.ScriptControllerSystem;
import com.indignado.logicbricks.systems.sensors.*;

/**
 * @author Rubentxu.
 */
public abstract class LogicBricksTest implements ApplicationListener {
    private final float HEIGHT = 20;
    protected World world;
    protected Engine engine;
    protected OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    private Box2DDebugRenderer renderer;
    private float WIDTH = 30;

    @Override
    public void create() {
        this.renderer = new Box2DDebugRenderer();
        this.camera = new OrthographicCamera(WIDTH, HEIGHT);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.world = new World(new Vector2(0, -10), true);
        this.engine = new Engine();
        CollisionSensorSystem collisionSensorSystem = new CollisionSensorSystem();
        this.world.setContactListener(collisionSensorSystem);
        engine.addSystem(collisionSensorSystem);
        engine.addSystem(new AlwaysSensorSystem());
        engine.addSystem(new DelaySensorSystem());
        KeyboardSensorSystem keyboardSensorSystem = new KeyboardSensorSystem();
        engine.addSystem(keyboardSensorSystem);
        MouseSensorSystem mouseSensorSystem = new MouseSensorSystem();
        engine.addSystem(mouseSensorSystem);
        engine.addSystem(new PropertySensorSystem());
        engine.addSystem(new ConditionalControllerSystem());
        engine.addSystem(new ScriptControllerSystem());
        engine.addSystem(new CameraActuatorSystem());
        engine.addSystem(new MessageActuatorSystem());
        engine.addSystem(new MotionActuatorSystem());
        engine.addSystem(new RigidBodyPropertyActuatorSystem());
        engine.addSystem(new StateActuatorSystem());
        engine.addSystem(new ViewActuatorSystem());
        engine.addSystem(new PropertyActuatorSystem());
        RenderingSystem renderingSystem = new RenderingSystem(batch, camera);
        renderingSystem.WIDTH = WIDTH;
        renderingSystem.HEIGHT = HEIGHT;
        engine.addSystem(renderingSystem);
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem());

        Gdx.input.setInputProcessor(new InputMultiplexer(keyboardSensorSystem, mouseSensorSystem));
        createWorld(world, engine);

        engine.update(0);

    }


    protected abstract void createWorld(World world, Engine engine);


    @Override
    public void resize(int width, int height) {
        Gdx.app.log("TEST", "Resize : " + width + " " + height);

    }


    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (deltaTime > 0.1f) deltaTime = 0.1f;
        engine.update(deltaTime);
        world.step(deltaTime, 3, 3);
        camera.update();
        renderer.render(world, camera.combined);

    }


    @Override
    public void pause() {
        engine.getSystem(CollisionSensorSystem.class).setProcessing(false);
        engine.getSystem(DelaySensorSystem.class).setProcessing(false);
        engine.getSystem(KeyboardSensorSystem.class).setProcessing(false);
        engine.getSystem(MouseSensorSystem.class).setProcessing(false);
        engine.getSystem(PropertySensorSystem.class).setProcessing(false);
        engine.getSystem(ConditionalControllerSystem.class).setProcessing(false);
        engine.getSystem(ScriptControllerSystem.class).setProcessing(false);
        engine.getSystem(CameraActuatorSystem.class).setProcessing(false);
        engine.getSystem(MessageActuatorSystem.class).setProcessing(false);
        engine.getSystem(MotionActuatorSystem.class).setProcessing(false);
        engine.getSystem(RenderingSystem.class).setProcessing(false);
        engine.getSystem(StateSystem.class).setProcessing(false);

    }

    @Override
    public void resume() {
        engine.getSystem(CollisionSensorSystem.class).setProcessing(true);
        engine.getSystem(DelaySensorSystem.class).setProcessing(true);
        engine.getSystem(KeyboardSensorSystem.class).setProcessing(true);
        engine.getSystem(MouseSensorSystem.class).setProcessing(true);
        engine.getSystem(PropertySensorSystem.class).setProcessing(true);
        engine.getSystem(ConditionalControllerSystem.class).setProcessing(true);
        engine.getSystem(ScriptControllerSystem.class).setProcessing(true);
        engine.getSystem(CameraActuatorSystem.class).setProcessing(true);
        engine.getSystem(MessageActuatorSystem.class).setProcessing(true);
        engine.getSystem(MotionActuatorSystem.class).setProcessing(true);
        engine.getSystem(RenderingSystem.class).setProcessing(true);
        engine.getSystem(StateSystem.class).setProcessing(true);

    }


    @Override
    public void dispose() {

    }

}
