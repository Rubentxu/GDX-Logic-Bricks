package com.indignado.functional.test.base;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.systems.AnimationSystem;
import com.indignado.logicbricks.systems.RenderingSystem;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.systems.ViewSystem;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.systems.sensors.KeyboardSensorSystem;
import com.indignado.logicbricks.systems.sensors.MouseSensorSystem;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.box2d.FixtureDefBuilder;

import java.io.File;
import java.net.URL;

/**
 * @author Rubentxu.
 */
public abstract class LogicBricksTest implements ApplicationListener {
    private final float HEIGHT = 20;
    protected World world;
    protected Engine engine;
    protected OrthographicCamera camera;
    protected BodyBuilder bodyBuilder;
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
        this.bodyBuilder = new BodyBuilder(world);

        // System
        RenderingSystem renderingSystem = new RenderingSystem(batch, camera);
        renderingSystem.WIDTH = WIDTH;
        renderingSystem.HEIGHT = HEIGHT;
        engine.addSystem(renderingSystem);
        engine.addSystem(new ViewSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem());

        createWorld(world, engine);

        InputMultiplexer input = new InputMultiplexer();
        if (engine.getSystem(KeyboardSensorSystem.class) != null)
            input.addProcessor(engine.getSystem(KeyboardSensorSystem.class));
        if (engine.getSystem(MouseSensorSystem.class) != null)
            input.addProcessor(engine.getSystem(MouseSensorSystem.class));
        Gdx.input.setInputProcessor(input);
        if (engine.getSystem(CollisionSensorSystem.class) != null)
            this.world.setContactListener(engine.getSystem(CollisionSensorSystem.class));

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
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(false);
        }

    }

    @Override
    public void resume() {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(true);
        }

    }


    @Override
    public void dispose() {

    }


    protected FileHandle getFileHandle(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        File file = new File(url.getPath());
        FileHandle fileHandle = new FileHandle(file);
        return fileHandle;

    }


    protected Body wall(float x, float y, float width, float height) {
        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "wall"));
        return bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(width, height)
                .restitution(0.4f)
                .friction(0.5f))
                .position(x, y)
                .userData(context)
                .build();

    }


    private Body crate(float x, float y, float width, float height) {
        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "crate"));
        return bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(width, height)
                .restitution(0.4f)
                .friction(0.5f))
                .userData(context)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

    }

}
