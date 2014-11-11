package com.indignado.functional.test;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.functional.test.base.LogicBricksTest;
import com.indignado.logicbricks.bricks.actuators.CameraActuator;
import com.indignado.logicbricks.bricks.actuators.MotionActuator;
import com.indignado.logicbricks.bricks.actuators.RigidBodyPropertyActuator;
import com.indignado.logicbricks.bricks.actuators.ViewActuator;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.KeyboardSensor;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.components.sensors.AlwaysSensorComponent;
import com.indignado.logicbricks.data.View;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;;import java.io.File;
import java.net.URL;

/**
 * @author Rubentxu.
 */
public class SimpleTest extends LogicBricksTest {
    private BodyBuilder bodyBuilder;
    private World world;
    private Engine engine;
    private int IdleState = 1;
    private int WalkingState = 0;



    public static void main(String[] args){
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;

        new LwjglApplication(new SimpleTest() , config);
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


        Body ground = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
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


        Entity player = new Entity();
        player.add(new StateComponent());

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


        TextureAtlas atlas = new TextureAtlas(getFileHandle("assets/animations/sprites.pack"));
        Array<TextureAtlas.AtlasRegion> heroWalking = atlas.findRegions("Andando");
        Array<TextureAtlas.AtlasRegion> heroJump = atlas.findRegions("Saltando");
        Array<TextureAtlas.AtlasRegion> heroFall = atlas.findRegions("Cayendo");
        Array<TextureAtlas.AtlasRegion> heroIdle = atlas.findRegions("Parado");

        Animation walking = new Animation(0.02f, heroWalking, Animation.PlayMode.LOOP);
        Animation jump = new Animation(0.02f * 7, heroJump, Animation.PlayMode.NORMAL);
        Animation fall = new Animation(0.02f * 5, heroFall, Animation.PlayMode.NORMAL);
        Animation idle = new Animation(0.02f * 4, heroIdle, Animation.PlayMode.LOOP);


        View playerView = new View();
        playerView.height = 2;
        playerView.width = 1.5f;
        playerView.transform = bodyPlayer.getTransform();
        playerView.animations = new IntMap<>();
        playerView.animations.put(IdleState,idle);
        playerView.animations.put(WalkingState,walking);
        playerView.layer = 1;

        ViewsComponent viewsComponent =  new ViewsComponent();
        viewsComponent.views.add(playerView);

        player.add(viewsComponent);

        KeyboardSensor keyboardSensor = new KeyboardSensor();
        keyboardSensor.keyCode = Input.Keys.D;

        ConditionalController controller = new ConditionalController();
        controller.type = ConditionalController.Type.AND;


        MotionActuator motionActuator = new MotionActuator();
        motionActuator.impulse = new Vector2(1,0);
        motionActuator.owner = player;
        motionActuator.limitVelocityX = 7;


        KeyboardSensor keyboardSensor2 = new KeyboardSensor();
        keyboardSensor2.keyCode = Input.Keys.A;

        ConditionalController controller2 = new ConditionalController();
        controller2.type = ConditionalController.Type.AND;


        MotionActuator motionActuator2 = new MotionActuator();
        motionActuator2.impulse = new Vector2(-1,0);
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

        new LogicBricksBuilder(player)
                .addSensor(keyboardSensor, IdleState, WalkingState)
                .addController(controller, IdleState, WalkingState)
                .connect(keyboardSensor)
                .addActuator(motionActuator,IdleState,WalkingState)
                .connect(controller)
                .addActuator(viewActuator,IdleState,WalkingState)
                .connect(controller);

        ViewActuator viewActuator2 = new ViewActuator();
        viewActuator2.flipX = true;
        viewActuator2.targetView = playerView;

        new LogicBricksBuilder(player)
                .addSensor(keyboardSensor2, IdleState, WalkingState)
                .addController(controller2, IdleState, WalkingState)
                .connect(keyboardSensor2)
                .addActuator(motionActuator2, IdleState, WalkingState)
                .connect(controller2)
                .addActuator(viewActuator2, IdleState, WalkingState)
                .connect(controller2);


        AlwaysSensor alwaysSensor = new AlwaysSensor();
        new LogicBricksBuilder(player)
                .addSensor(alwaysSensor, IdleState, WalkingState)
                .addController(controller3, IdleState, WalkingState)
                .connect(alwaysSensor)
                .addActuator(cameraActuator, IdleState, WalkingState)
                .connect(controller3);


        ConditionalController controller4 = new ConditionalController();
        controller4.type = ConditionalController.Type.NOR;

        RigidBodyPropertyActuator rigidBodyPropertyActuator1 = new RigidBodyPropertyActuator();
        rigidBodyPropertyActuator1.friction = 40;
        rigidBodyPropertyActuator1.targetRigidBody = bodyPlayer;

        new LogicBricksBuilder(player)
                .addSensor(keyboardSensor, IdleState, WalkingState)
                .addSensor(keyboardSensor2, IdleState, WalkingState)
                .addController(controller4, IdleState, WalkingState)
                .connect(keyboardSensor)
                .connect(keyboardSensor2)
                .addActuator(rigidBodyPropertyActuator1, IdleState, WalkingState)
                .connect(controller4);



        ConditionalController controller5 = new ConditionalController();
        controller5.type = ConditionalController.Type.OR;

        RigidBodyPropertyActuator rigidBodyPropertyActuator2 = new RigidBodyPropertyActuator();
        rigidBodyPropertyActuator2.friction = 0.3f;
        rigidBodyPropertyActuator2.targetRigidBody = bodyPlayer;

        new LogicBricksBuilder(player)
                .addSensor(keyboardSensor, IdleState, WalkingState)
                .addSensor(keyboardSensor2, IdleState, WalkingState)
                .addController(controller5, IdleState, WalkingState)
                .connect(keyboardSensor)
                .connect(keyboardSensor2)
                .addActuator(rigidBodyPropertyActuator2, IdleState, WalkingState)
                .connect(controller5);

        System.out.println("Always sensors size: "+ player.getComponent(AlwaysSensorComponent.class).sensors.size);
        engine.addEntity(player);
    }

}
