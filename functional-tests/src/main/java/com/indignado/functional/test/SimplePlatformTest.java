package com.indignado.functional.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.Body;
import com.indignado.functional.test.base.LogicBricksTest;
import com.indignado.functional.test.levels.FlyingDartLevel;
import com.indignado.logicbricks.utils.builders.BodyBuilder;


/**
 * @author Rubentxu.
 */
public class SimplePlatformTest extends LogicBricksTest {
    private BodyBuilder bodyBuilder;
    private Body ground;
    private Animation walking;
    private Animation idle;
    private Animation jump;
    private Animation fall;
    private ParticleEffect dustEffect;


    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;

        new LwjglApplication(new SimplePlatformTest(), config);
    }


    @Override
    public void create() {
        super.create();
        addLevel(new FlyingDartLevel());


    }

    /*@Override
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


        dustEffect = new ParticleEffect();
        dustEffect.load(getFileHandle("assets/particles/dust.pfx"), getFileHandle("assets/particles"));

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
        blackBoardComponent.addProperty(new Property<Boolean>("isGround", false));
        player.add(blackBoardComponent);

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

        AnimationView playerView = new AnimationView();
        playerView.setHeight(2.3f);
        playerView.setWidth(1.8f);
        playerView.setAttachedTransform(bodyPlayer.getTransform());
        playerView.animations = new IntMap<>();
        playerView.animations.put(stateComponent.getState("Idle"), idle);
        playerView.animations.put(stateComponent.getState("Walking"), walking);
        playerView.setLayer(1);

        ParticleEffectView particleEffectView = new ParticleEffectView();
        particleEffectView.setAttachedTransform(bodyPlayer.getTransform());
        particleEffectView.effect = dustEffect;
        particleEffectView.setLocalPosition(new Vector2(0, -1));
        particleEffectView.setTint(Color.BLUE);

        ViewsComponent viewsComponent = new ViewsComponent();
        viewsComponent.views.add(playerView);
        viewsComponent.views.add(particleEffectView);

        player.add(viewsComponent);

        KeyboardSensor keyboardSensor = new KeyboardSensor();
        keyboardSensor.setKeyCode(Input.Keys.D);

        ConditionalController controller = new ConditionalController();
        controller.setType(ConditionalController.Type.AND);


        MotionActuator motionActuator = new MotionActuator();
        motionActuator.setImpulse(new Vector2(1, 0));
        motionActuator.setOwner(player);
        motionActuator.setLimitVelocityX(7);


        KeyboardSensor keyboardSensor2 = new KeyboardSensor();
        keyboardSensor2.setKeyCode(Input.Keys.A);

        ConditionalController controller2 = new ConditionalController();
        controller2.setType(ConditionalController.Type.AND);


        MotionActuator motionActuator2 = new MotionActuator();
        motionActuator2.setImpulse(new Vector2(-1, 0));
        motionActuator2.setOwner(player);
        motionActuator2.setLimitVelocityX(7);


        ConditionalController controller3 = new ConditionalController();
        controller3.setType(ConditionalController.Type.AND);


        CameraActuator cameraActuator = new CameraActuator();
        cameraActuator.setHeight((short) 1);
        cameraActuator.setTarget(player);
        cameraActuator.setCamera(camera);


        TextureActuator textureActuator = new TextureActuator();
        textureActuator.setFlipX(false);
        textureActuator.setTextureView(playerView);


        StateActuator stateActuator = new StateActuator();
        stateActuator.setOwner(player);
        stateActuator.setState(1);

        StateActuator stateActuatorB = new StateActuator();
        stateActuatorB.setOwner(player);
        stateActuatorB.setState(1);

        StateActuator stateActuator2 = new StateActuator();
        stateActuator2.setOwner(player);
        stateActuator2.setState(0);

        EntityBuilder builder = new EntityBuilder(engine);

        builder.addController(controller, "Idle", "Walking")
                .connectToSensor(keyboardSensor)
                .connectToActuator(motionActuator)
                .connectToActuator(textureActuator)
                .connectToActuator(stateActuator);


        TextureActuator textureActuator2 = new TextureActuator();
        textureActuator2.setFlipX(true);
        textureActuator2.setTextureView(playerView);

        builder.addController(controller2, "Idle", "Walking")
                .connectToSensor(keyboardSensor2)
                .connectToActuator(motionActuator2)
                .connectToActuator(textureActuator2)
                .connectToActuator(stateActuatorB);


        AlwaysSensor alwaysSensor = new AlwaysSensor();
        builder.addController(controller3, "Idle", "Walking")
                .connectToSensor(alwaysSensor)
                .connectToActuator(cameraActuator);


        ConditionalController controller4 = new ConditionalController();
        controller4.setType(ConditionalController.Type.NOR);

        EditRigidBodyActuator editRigidBodyActuator1 = new EditRigidBodyActuator();
        editRigidBodyActuator1.setFriction(40);
        editRigidBodyActuator1.setTargetRigidBody(bodyPlayer);

        builder.addController(controller4, "Idle", "Walking")
                .connectToSensor(keyboardSensor)
                .connectToSensor(keyboardSensor2)
                .connectToActuator(editRigidBodyActuator1)
                .connectToActuator(stateActuator2);


        ConditionalController controller5 = new ConditionalController();
        controller5.setType(ConditionalController.Type.OR);

        EditRigidBodyActuator editRigidBodyActuator2 = new EditRigidBodyActuator();
        editRigidBodyActuator2.setFriction(0.3f);
        editRigidBodyActuator2.setTargetRigidBody(bodyPlayer);

        builder.addController(controller5, "Idle", "Walking")
                .connectToSensor(keyboardSensor)
                .connectToSensor(keyboardSensor2)
                .connectToActuator(editRigidBodyActuator2);


        CollisionSensor collisionSensor = new CollisionSensor();
        collisionSensor.setOwnerRigidBody(bodyPlayer);
        collisionSensor.setTargetRigidBody(ground);

        ConditionalController controllerGround = new ConditionalController();
        controllerGround.setType(ConditionalController.Type.AND);

        ConditionalController controllerNotGround = new ConditionalController();
        controllerNotGround.setType(ConditionalController.Type.NOR);

        PropertyActuator<Boolean> propertyActuator = new PropertyActuator();
        propertyActuator.setOwner(player);
        propertyActuator.setProperty("isGround");
        propertyActuator.setValue(true);
        propertyActuator.setMode(PropertyActuator.Mode.Assign);


        PropertyActuator<Boolean> propertyActuator2 = new PropertyActuator();
        propertyActuator2.setOwner(player);
        propertyActuator2.setProperty("isGround");
        propertyActuator2.setValue(false);
        propertyActuator2.setMode(PropertyActuator.Mode.Assign);


        builder.addController(controllerGround, "Idle", "Walking")
                .connectToSensor(collisionSensor)
                .connectToActuator(propertyActuator);


        builder.addController(controllerNotGround, "Idle", "Walking")
                .connectToSensor(collisionSensor)
                .connectToActuator(propertyActuator2);


        KeyboardSensor keySensorJump = new KeyboardSensor();
        keySensorJump.setKeyCode(Input.Keys.W);

        PropertySensor propertySensorIsGround = new PropertySensor<Boolean>();
        propertySensorIsGround.setProperty("isGround");
        propertySensorIsGround.setEvaluationType(PropertySensor.EvaluationType.EQUAL);
        propertySensorIsGround.setValue(true);

        ConditionalController controllerJump = new ConditionalController();
        controllerJump.setType(ConditionalController.Type.AND);


        MotionActuator motionActuatorJump = new MotionActuator();
        motionActuatorJump.setImpulse(new Vector2(0, 3));
        motionActuatorJump.setOwner(player);
        motionActuatorJump.setLimitVelocityY(7);


        builder.addController(controllerJump, "Idle", "Walking")
                .connectToSensor(keySensorJump)
                .connectToSensor(propertySensorIsGround)
                .connectToActuator(motionActuatorJump);

        return builder.build(player);

    }*/

}
