package com.indignado.functional.test;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.functional.test.base.LogicBricksTest;
import com.indignado.logicbricks.bricks.actuators.*;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.CollisionSensor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.data.Property;
import com.indignado.logicbricks.data.View;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;

import java.io.File;
import java.net.URL;

;

/**
 * @author Rubentxu.
 */
public class SimplePlatformTest extends LogicBricksTest {
    private BodyBuilder bodyBuilder;
    private World world;
    private Engine engine;
    private Body ground;
    private Animation walking;
    private Animation idle;
    private Animation jump;
    private Animation fall;


    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;

        new LwjglApplication(new SimplePlatformTest(), config);
    }


    public FileHandle getFileHandle(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        File file = new File(url.getPath());
        FileHandle fileHandle = new FileHandle(file);
        return fileHandle;

    }


    @Override
    protected void createWorld(World world, Engine engine) {
        this.world = world;
        this.engine = engine;
        bodyBuilder = new BodyBuilder(world);


        ground = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(50, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(0, 0)
                .mass(1)
                .build();


        bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(1, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(3, 5)
                .mass(1)
                .build();


        bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(1, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(9, 7)
                .mass(1)
                .build();


        TextureAtlas atlas = new TextureAtlas(getFileHandle("assets/animations/sprites.pack"));
        Array<TextureAtlas.AtlasRegion> heroWalking = atlas.findRegions("Andando");
        Array<TextureAtlas.AtlasRegion> heroJump = atlas.findRegions("Saltando");
        Array<TextureAtlas.AtlasRegion> heroFall = atlas.findRegions("Cayendo");
        Array<TextureAtlas.AtlasRegion> heroIdle = atlas.findRegions("Parado");

        walking = new Animation(0.02f, heroWalking, Animation.PlayMode.LOOP);
        jump = new Animation(0.02f * 7, heroJump, Animation.PlayMode.NORMAL);
        fall = new Animation(0.02f * 5, heroFall, Animation.PlayMode.NORMAL);
        idle = new Animation(0.02f * 4, heroIdle, Animation.PlayMode.LOOP);

        engine.addEntity(createPlayer());

    }


    private Entity createPlayer() {
        Entity player = new Entity();

        StateComponent stateComponent = new StateComponent();
        stateComponent.createState("Idle");
        stateComponent.createState("Walking");
        player.add(stateComponent);


        BlackBoardComponent blackBoardComponent = new BlackBoardComponent();
        blackBoardComponent.add(new Property<Boolean>("isGround", false));

        Body bodyPlayer = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(0.35f, 1))
                .type(BodyDef.BodyType.DynamicBody)
                .position(0, 5)
                .mass(1)
                .userData(player)
                .build();

        RigidBodiesComponents rbc = new RigidBodiesComponents();
        rbc.rigidBodies.add(bodyPlayer);
        player.add(rbc);

        View playerView = new View();
        playerView.height = 2;
        playerView.width = 1.5f;
        playerView.transform = bodyPlayer.getTransform();
        playerView.animations = new IntMap<>();
        playerView.animations.put(stateComponent.getState("Idle"), idle);
        playerView.animations.put(stateComponent.getState("Walking"), walking);
        playerView.layer = 1;

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(playerView);

        player.add(viewsComponent);

        KeyboardSensor keyboardSensor = new KeyboardSensor();
        keyboardSensor.keyCode = Input.Keys.D;

        ConditionalController controller = new ConditionalController();
        controller.type = ConditionalController.Type.AND;


        MotionActuator motionActuator = new MotionActuator();
        motionActuator.impulse = new Vector2(1, 0);
        motionActuator.owner = player;
        motionActuator.limitVelocityX = 7;


        KeyboardSensor keyboardSensor2 = new KeyboardSensor();
        keyboardSensor2.keyCode = Input.Keys.A;

        ConditionalController controller2 = new ConditionalController();
        controller2.type = ConditionalController.Type.AND;


        MotionActuator motionActuator2 = new MotionActuator();
        motionActuator2.impulse = new Vector2(-1, 0);
        motionActuator2.owner = player;
        motionActuator2.limitVelocityX = 7;


        ConditionalController controller3 = new ConditionalController();
        controller3.type = ConditionalController.Type.AND;


        CameraActuator cameraActuator = new CameraActuator();
        cameraActuator.height = 1;
        cameraActuator.target = player;
        cameraActuator.camera = camera;


        ViewActuator viewActuator = new ViewActuator();
        viewActuator.flipX = false;
        viewActuator.targetView = playerView;


        StateActuator stateActuator = new StateActuator();
        stateActuator.owner = player;
        stateActuator.state = 1;

        StateActuator stateActuatorB = new StateActuator();
        stateActuatorB.owner = player;
        stateActuatorB.state = 1;

        StateActuator stateActuator2 = new StateActuator();
        stateActuator2.owner = player;
        stateActuator2.state = 0;

        LogicBricksBuilder builder = new LogicBricksBuilder(player);

        builder.addSensor(keyboardSensor, "Idle", "Walking")
                .addController(controller, "Idle", "Walking")
                .connect(keyboardSensor)
                .addActuator(motionActuator, "Idle", "Walking")
                .connect(controller)
                .addActuator(viewActuator, "Idle", "Walking")
                .connect(controller)
                .addActuator(stateActuator, "Idle")
                .connect(controller);


        ViewActuator viewActuator2 = new ViewActuator();
        viewActuator2.flipX = true;
        viewActuator2.targetView = playerView;

        builder.addSensor(keyboardSensor2, "Idle", "Walking")
                .addController(controller2, "Idle", "Walking")
                .connect(keyboardSensor2)
                .addActuator(motionActuator2, "Idle", "Walking")
                .connect(controller2)
                .addActuator(viewActuator2, "Idle", "Walking")
                .connect(controller2)
                .addActuator(stateActuatorB, "Idle")
                .connect(controller2);


        AlwaysSensor alwaysSensor = new AlwaysSensor();
        builder.addSensor(alwaysSensor, "Idle", "Walking")
                .addController(controller3, "Idle", "Walking")
                .connect(alwaysSensor)
                .addActuator(cameraActuator, "Idle", "Walking")
                .connect(controller3);


        ConditionalController controller4 = new ConditionalController();
        controller4.type = ConditionalController.Type.NOR;

        RigidBodyPropertyActuator rigidBodyPropertyActuator1 = new RigidBodyPropertyActuator();
        rigidBodyPropertyActuator1.friction = 40;
        rigidBodyPropertyActuator1.targetRigidBody = bodyPlayer;

        builder.addSensor(keyboardSensor, "Idle", "Walking")
                .addSensor(keyboardSensor2, "Idle", "Walking")
                .addController(controller4, "Idle", "Walking")
                .connect(keyboardSensor)
                .connect(keyboardSensor2)
                .addActuator(rigidBodyPropertyActuator1, "Idle", "Walking")
                .connect(controller4)
                .addActuator(stateActuator2, "Walking")
                .connect(controller4);


        ConditionalController controller5 = new ConditionalController();
        controller5.type = ConditionalController.Type.OR;

        RigidBodyPropertyActuator rigidBodyPropertyActuator2 = new RigidBodyPropertyActuator();
        rigidBodyPropertyActuator2.friction = 0.3f;
        rigidBodyPropertyActuator2.targetRigidBody = bodyPlayer;

        builder.addSensor(keyboardSensor, "Idle", "Walking")
                .addSensor(keyboardSensor2, "Idle", "Walking")
                .addController(controller5, "Idle", "Walking")
                .connect(keyboardSensor)
                .connect(keyboardSensor2)
                .addActuator(rigidBodyPropertyActuator2, "Idle", "Walking")
                .connect(controller5);


        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.ownerRigidBody = bodyPlayer;
        collisionSensor.targetRigidBody = ground;

        ConditionalController controllerGround = new ConditionalController();
        controllerGround.type = ConditionalController.Type.AND;

        ConditionalController controllerNotGround = new ConditionalController();
        controllerNotGround.type = ConditionalController.Type.NOR;

        PropertyActuator<Boolean> propertyActuator = new PropertyActuator();
        propertyActuator.owner = player;
        propertyActuator.property = "isGround";
        propertyActuator.value = true;
        propertyActuator.mode = PropertyActuator.Mode.Assign;


        PropertyActuator<Boolean> propertyActuator2 = new PropertyActuator();
        propertyActuator2.owner = player;
        propertyActuator2.property = "isGround";
        propertyActuator2.value = false;
        propertyActuator2.mode = PropertyActuator.Mode.Assign;


        builder.addSensor(collisionSensor, "Idle", "Walking")
                .addController(controllerGround, "Idle", "Walking")
                .connect(collisionSensor)
                .addActuator(propertyActuator, "Idle", "Walking")
                .connect(controllerGround);

        builder.addSensor(collisionSensor, "Idle", "Walking")
                .addController(controllerNotGround, "Idle", "Walking")
                .connect(collisionSensor)
                .addActuator(propertyActuator2, "Idle", "Walking")
                .connect(controllerNotGround);

        return player;

    }

}
