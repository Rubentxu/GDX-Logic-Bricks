package com.indignado.functional.test.levels.simpleplatform.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.components.data.AnimationView;
import com.indignado.logicbricks.components.data.ParticleEffectView;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.core.actuators.*;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.core.sensors.KeyboardSensor;
import com.indignado.logicbricks.core.sensors.PropertySensor;
import com.indignado.logicbricks.core.sensors.TimerSensor;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.actuators.*;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.CollisionSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.KeyboardSensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.PropertySensorBuilder;
import com.indignado.logicbricks.utils.builders.sensors.TimerSensorBuilder;

/**
 * @author Rubentxu.
 */
public class PlayerPlatform extends EntityFactory {
    private String effect = "assets/particles/dust.pfx";
    private String atlas = "assets/animations/sprites.pack";


    public PlayerPlatform(World world) {
        super(world);
    }


    @Override
    public void loadAssets() {
        if (!world.getAssetManager().isLoaded(effect)) world.getAssetManager().load(effect, ParticleEffect.class);
        if (!world.getAssetManager().isLoaded(atlas)) world.getAssetManager().load(atlas, TextureAtlas.class);

    }


    @Override
    public Entity createEntity() {
        OrthographicCamera camera = world.getCamera();
        EntityBuilder entityBuilder = world.getEntityBuilder();
        entityBuilder.initialize();

        // Assets entity
        ParticleEffect dustEffect = world.getAssetManager().get(effect, ParticleEffect.class);
        TextureAtlas textureAtlas = world.getAssetManager().get(atlas, TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> heroWalking = textureAtlas.findRegions("Andando");
        Array<TextureAtlas.AtlasRegion> heroJump = textureAtlas.findRegions("Saltando");
        Array<TextureAtlas.AtlasRegion> heroFall = textureAtlas.findRegions("Cayendo");
        Array<TextureAtlas.AtlasRegion> heroIdle = textureAtlas.findRegions("Parado");

        Animation walking = new Animation(0.02f, heroWalking, Animation.PlayMode.LOOP);
        Animation jump = new Animation(0.02f * 7, heroJump, Animation.PlayMode.NORMAL);
        Animation fall = new Animation(0.02f * 5, heroFall, Animation.PlayMode.NORMAL);
        Animation idle = new Animation(0.02f * 4, heroIdle, Animation.PlayMode.LOOP);

        // Create General Components
        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Player";

        StateComponent stateComponent = entityBuilder.getComponent(StateComponent.class);
        stateComponent.createState("Idle");
        stateComponent.createState("Walking");

        BlackBoardComponent blackBoardComponent = entityBuilder.getComponent(BlackBoardComponent.class);
        blackBoardComponent.addProperty(new Property<Boolean>("isGround", false));

        Body bodyPlayer = world.getBodyBuilder().fixture(world.getBodyBuilder().fixtureDefBuilder()
                .boxShape(0.35f, 1)
                .density(1))
                .type(BodyDef.BodyType.DynamicBody)
                .fixedRotation()
                .position(0, 5)
                .mass(1)
                .build();

        RigidBodiesComponents rbc = entityBuilder.getComponent(RigidBodiesComponents.class);
        rbc.rigidBodies.add(bodyPlayer);


        AnimationView playerView = new AnimationView();
        playerView.setHeight(2.3f);
        playerView.setWidth(1.8f);
        playerView.setAttachedTransform(bodyPlayer.getTransform());
        playerView.animations = new IntMap<>();
        playerView.animations.put(stateComponent.getState("Idle"), idle);
        playerView.animations.put(stateComponent.getState("Walking"), walking);
        playerView.animations.put(stateComponent.getState("Jump"), jump);
        playerView.animations.put(stateComponent.getState("Fall"), fall);
        playerView.setLayer(1);

        ParticleEffectView particleEffectView = new ParticleEffectView();
        particleEffectView.setAttachedTransform(bodyPlayer.getTransform());
        particleEffectView.effect = dustEffect;
        particleEffectView.setLocalPosition(new Vector2(0, -1));
        particleEffectView.setTint(Color.BLUE);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(playerView);
        viewsComponent.views.add(particleEffectView);


        // Create Bricks Walking Right ----------------------------------------------------------------
        KeyboardSensor keyboardSensorWalkingRight = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.D)
                .getBrick();

        ConditionalController controllerWalkingRight = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();


        MotionActuator motionActuatorWalkingRight = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setImpulse(new Vector2(2, 0))
                .setLimitVelocityX(7)
                .getBrick();

        TextureActuator textureActuatorWalkingRight = BricksUtils.getBuilder(TextureActuatorBuilder.class)
                .setFlipX(false)
                .setTextureView(playerView)
                .getBrick();

        entityBuilder.addController(controllerWalkingRight, "Idle", "Walking")
                .connectToSensor(keyboardSensorWalkingRight)
                .connectToActuators(motionActuatorWalkingRight, textureActuatorWalkingRight);


        // Create Bricks Walking Left ----------------------------------------------------------------
        KeyboardSensor keyboardSensorWalkingLeft = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.A)
                .getBrick();

        ConditionalController controllerWalkingLeft = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        MotionActuator motionActuatorWalkingLeft = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setImpulse(new Vector2(-2, 0))
                .setLimitVelocityX(7)
                .getBrick();

        TextureActuator textureActuatorWalkingLeft = BricksUtils.getBuilder(TextureActuatorBuilder.class)
                .setFlipX(true)
                .setTextureView(playerView)
                .getBrick();

        entityBuilder.addController(controllerWalkingLeft, "Idle", "Walking")
                .connectToSensor(keyboardSensorWalkingLeft)
                .connectToActuators(motionActuatorWalkingLeft, textureActuatorWalkingLeft);


        // Create Bricks Camera ----------------------------------------------------------------
        TimerSensor timerSensorCamera = BricksUtils.getBuilder(TimerSensorBuilder.class)
                .setRepeat(true)
                .getBrick();

        ConditionalController controllerCamera = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        CameraActuator cameraActuator = BricksUtils.getBuilder(CameraActuatorBuilder.class)
                .setHeight((short) 1)
                .setCamera(camera)
                .getBrick();

        entityBuilder.addController(controllerCamera, "Idle", "Walking", "Jump", "Fall")
                .connectToSensor(timerSensorCamera)
                .connectToActuator(cameraActuator);


        // Create Bricks Idle ----------------------------------------------------------------
        ConditionalController controllerIdle = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_NOR)
                .getBrick();

        EditRigidBodyActuator editRigidBodyActuatorIdle = BricksUtils.getBuilder(EditRigidBodyActuatorBuilder.class)
                .setFriction(40)
                .setTargetRigidBody(bodyPlayer)
                .getBrick();

        StateActuator stateActuatorIdle = new StateActuator();
        stateActuatorIdle.state = stateComponent.getState("Idle");

        entityBuilder.addController(controllerIdle, "Walking")
                .connectToSensors(keyboardSensorWalkingRight, keyboardSensorWalkingLeft)
                .connectToActuators(editRigidBodyActuatorIdle, stateActuatorIdle);


        // Create Bricks Walking ----------------------------------------------------------------
        ConditionalController controllerWalking = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_OR)
                .getBrick();

        EditRigidBodyActuator editRigidBodyActuatorWalking = BricksUtils.getBuilder(EditRigidBodyActuatorBuilder.class)
                .setFriction(0.3f)
                .setTargetRigidBody(bodyPlayer)
                .getBrick();

        StateActuator stateActuatorWalking = new StateActuator();
        stateActuatorWalking.state = stateComponent.getState("Walking");

        entityBuilder.addController(controllerWalking, "Idle")
                .connectToSensors(keyboardSensorWalkingRight, keyboardSensorWalkingLeft)
                .connectToActuators(editRigidBodyActuatorWalking, stateActuatorWalking);

        // Create Bricks Ground ----------------------------------------------------------------
        CollisionSensor collisionSensorGround = BricksUtils.getBuilder(CollisionSensorBuilder.class)
                .setTargetName("Ground")
                .getBrick();

        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        PropertyActuator<Boolean> propertyActuatorInGround = BricksUtils.getBuilder(PropertyActuatorBuilder.class)
                .setProperty("isGround")
                .setValue(true)
                .setMode(PropertyActuator.Mode.Assign)
                .getBrick();

        EditRigidBodyActuator editRigidBodyActuatorGround = BricksUtils.getBuilder(EditRigidBodyActuatorBuilder.class)
                .setFriction(40)
                .setTargetRigidBody(bodyPlayer)
                .getBrick();

        StateActuator stateActuatorIdleGround = new StateActuator();
        stateActuatorWalking.state = stateComponent.getState("Idle");

        entityBuilder.addController(controllerGround, "Jump")
                .connectToSensor(collisionSensorGround)
                .connectToActuators(propertyActuatorInGround, editRigidBodyActuatorGround, stateActuatorIdleGround);

        // Create Bricks not Ground ----------------------------------------------------------------
        ConditionalController controllerNotGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_NOR)
                .getBrick();

        PropertyActuator<Boolean> propertyActuatorNotInGround = BricksUtils.getBuilder(PropertyActuatorBuilder.class)
                .setProperty("isGround")
                .setValue(false)
                .setMode(PropertyActuator.Mode.Assign)
                .getBrick();

        entityBuilder.addController(controllerNotGround, "Idle", "Walking")
                .connectToSensor(collisionSensorGround)
                .connectToActuator(propertyActuatorNotInGround);


        // Create Bricks Jump ----------------------------------------------------------------
        KeyboardSensor keySensorJump = BricksUtils.getBuilder(KeyboardSensorBuilder.class)
                .setKeyCode(Input.Keys.W)
                .getBrick();

        PropertySensor propertySensorIsGround = BricksUtils.getBuilder(PropertySensorBuilder.class)
                .setProperty("isGround")
                .setEvaluationType(PropertySensor.EvaluationType.EQUAL)
                .setValue(true)
                .getBrick();

        ConditionalController controllerJump = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();


        MotionActuator motionActuatorJump = BricksUtils.getBuilder(MotionActuatorBuilder.class)
                .setImpulse(new Vector2(0, 3))
                .setLimitVelocityY(7)
                .getBrick();

        StateActuator stateActuatorJump = new StateActuator();
        stateActuatorWalking.state = stateComponent.getState("Jump");

        entityBuilder.addController(controllerJump, "Idle", "Walking")
                .connectToSensors(keySensorJump, propertySensorIsGround)
                .connectToActuator(motionActuatorJump);


        // Effect On
        TimerSensor timerEffectOn = BricksUtils.getBuilder(TimerSensorBuilder.class)
                .setRepeat(true)
                .getBrick();

        ConditionalController controllerEffectOn = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();


        EffectActuator effectActuatorOn = BricksUtils.getBuilder(EffectActuatorBuilder.class)
                .setActive(false)
                .setEffectView(particleEffectView)
                .getBrick();

        entityBuilder.addController(controllerEffectOn, "Walking")
                .connectToSensor(timerEffectOn)
                .connectToActuator(effectActuatorOn);

        // Effect Off
        TimerSensor timerEffectOff = BricksUtils.getBuilder(TimerSensorBuilder.class)
                .setRepeat(true)
                .getBrick();

        ConditionalController controllerEffectOff = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        EffectActuator effectActuatorOff = BricksUtils.getBuilder(EffectActuatorBuilder.class)
                .setActive(true)
                .setEffectView(particleEffectView)
                .getBrick();

        entityBuilder.addController(controllerEffectOff, "Idle")
                .connectToSensor(timerEffectOff)
                .connectToActuator(effectActuatorOff);


        return entityBuilder.getEntity();

    }

}
